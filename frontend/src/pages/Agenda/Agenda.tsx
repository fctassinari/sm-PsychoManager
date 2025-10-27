import React, { useState } from 'react';
import {
  Box,
  Button,
  Card,
  CardContent,
  CardHeader,
  Chip,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  FormControl,
  FormControlLabel,
  Grid,
  IconButton,
  InputLabel,
  MenuItem,
  Paper,
  Radio,
  RadioGroup,
  Select,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TextField,
  Typography,
  Toolbar,
  Autocomplete,
} from '@mui/material';
import {
  Add as AddIcon,
  Edit as EditIcon,
  Delete as DeleteIcon,
  CheckCircle as CheckCircleIcon,
  Schedule as ScheduleIcon,
  Search as SearchIcon,
  FilterList as FilterIcon,
} from '@mui/icons-material';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import moment from 'moment';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import { useQuery, useMutation, useQueryClient } from 'react-query';
import { useNavigate } from 'react-router-dom';
import { format } from 'date-fns';
import { ptBR } from 'date-fns/locale';
import toast from 'react-hot-toast';
import { agendaService, pacienteService, produtoService } from '../../services/api';
import { Agenda as AgendaType, Paciente, Produto, AgendaFormData } from '../../types';
import { STATUS_PAGAMENTO_OPTIONS } from '../../types';

const localizer = momentLocalizer(moment);

const Agenda: React.FC = () => {
  const [view, setView] = useState<'month' | 'week' | 'day'>('month');
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [agendaDialog, setAgendaDialog] = useState<{
    open: boolean;
    agenda: AgendaType | null;
    isEdit: boolean;
  }>({ open: false, agenda: null, isEdit: false });
  const [filters, setFilters] = useState({
    dataInicio: format(new Date(), 'yyyy-MM-dd'),
    dataFim: format(new Date(), 'yyyy-MM-dd'),
    idPaciente: '',
    stPresenca: '',
    stPagamento: '',
  });

  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const { data: agendamentos = [] } = useQuery(
    ['agendamentos', filters],
    () => {
      if (filters.dataInicio && filters.dataFim) {
        return agendaService.buscarPorPeriodo(filters.dataInicio, filters.dataFim).then(res => res.data);
      }
      return agendaService.listar().then(res => res.data);
    }
  );

  const { data: pacientes = [] } = useQuery('pacientes', () =>
    pacienteService.listar().then(res => res.data)
  );

  const { data: produtos = [] } = useQuery('produtos', () =>
    produtoService.listar().then(res => res.data)
  );

  const createMutation = useMutation(
    (data: AgendaFormData) => agendaService.criar(data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries('agendamentos');
        toast.success('Agendamento criado com sucesso!');
        setAgendaDialog({ open: false, agenda: null, isEdit: false });
      },
      onError: (error: any) => {
        toast.error('Erro ao criar agendamento: ' + (error.response?.data?.error || error.message));
      },
    }
  );

  const updateMutation = useMutation(
    ({ idPaciente, dtConsulta, data }: { idPaciente: number; dtConsulta: string; data: AgendaFormData }) =>
      agendaService.atualizar(idPaciente, dtConsulta, data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries('agendamentos');
        toast.success('Agendamento atualizado com sucesso!');
        setAgendaDialog({ open: false, agenda: null, isEdit: false });
      },
      onError: (error: any) => {
        toast.error('Erro ao atualizar agendamento: ' + (error.response?.data?.error || error.message));
      },
    }
  );

  const deleteMutation = useMutation(
    ({ idPaciente, dtConsulta }: { idPaciente: number; dtConsulta: string }) =>
      agendaService.excluir(idPaciente, dtConsulta),
    {
      onSuccess: () => {
        queryClient.invalidateQueries('agendamentos');
        toast.success('Agendamento excluído com sucesso!');
      },
      onError: (error: any) => {
        toast.error('Erro ao excluir agendamento: ' + (error.response?.data?.error || error.message));
      },
    }
  );

  const confirmPresencaMutation = useMutation(
    ({ idPaciente, dtConsulta }: { idPaciente: number; dtConsulta: string }) =>
      agendaService.confirmarPresenca(idPaciente, dtConsulta),
    {
      onSuccess: () => {
        queryClient.invalidateQueries('agendamentos');
        toast.success('Presença confirmada com sucesso!');
      },
      onError: (error: any) => {
        toast.error('Erro ao confirmar presença: ' + (error.response?.data?.error || error.message));
      },
    }
  );

  const updatePagamentoMutation = useMutation(
    ({ idPaciente, dtConsulta, status }: { idPaciente: number; dtConsulta: string; status: number }) =>
      agendaService.atualizarPagamento(idPaciente, dtConsulta, status),
    {
      onSuccess: () => {
        queryClient.invalidateQueries('agendamentos');
        toast.success('Status de pagamento atualizado com sucesso!');
      },
      onError: (error: any) => {
        toast.error('Erro ao atualizar pagamento: ' + (error.response?.data?.error || error.message));
      },
    }
  );

  const events = agendamentos.map((agenda) => ({
    id: `${agenda.idPaciente}-${agenda.dtConsulta}`,
    title: `${agenda.nomePaciente} - ${agenda.nomeProduto}`,
    start: new Date(agenda.dtConsulta),
    end: agenda.dtConsultaAte ? new Date(agenda.dtConsultaAte) : new Date(agenda.dtConsulta),
    resource: agenda,
  }));

  const handleSelectEvent = (event: any) => {
    setAgendaDialog({
      open: true,
      agenda: event.resource,
      isEdit: true,
    });
  };

  const handleSelectSlot = (slotInfo: any) => {
    setAgendaDialog({
      open: true,
      agenda: null,
      isEdit: false,
    });
  };

  const handleCloseDialog = () => {
    setAgendaDialog({ open: false, agenda: null, isEdit: false });
  };

  const handleDelete = (agenda: AgendaType) => {
    if (window.confirm('Tem certeza que deseja excluir este agendamento?')) {
      deleteMutation.mutate({
        idPaciente: agenda.idPaciente,
        dtConsulta: agenda.dtConsulta,
      });
    }
  };

  const handleConfirmPresenca = (agenda: AgendaType) => {
    confirmPresencaMutation.mutate({
      idPaciente: agenda.idPaciente,
      dtConsulta: agenda.dtConsulta,
    });
  };

  const handleUpdatePagamento = (agenda: AgendaType, status: number) => {
    updatePagamentoMutation.mutate({
      idPaciente: agenda.idPaciente,
      dtConsulta: agenda.dtConsulta,
      status,
    });
  };

  const getStatusPagamentoDescricao = (status: number) => {
    return STATUS_PAGAMENTO_OPTIONS.find(opt => opt.value === status)?.label || 'Desconhecido';
  };

  const getStatusPagamentoColor = (status: number) => {
    const colors = {
      0: 'default',
      1: 'success',
      2: 'warning',
      3: 'error',
    };
    return colors[status as keyof typeof colors] || 'default';
  };

  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        Agenda
      </Typography>

      <Card>
        <CardHeader
          title="Calendário de Agendamentos"
          action={
            <Box display="flex" gap={1}>
              <Button
                variant={view === 'month' ? 'contained' : 'outlined'}
                onClick={() => setView('month')}
                size="small"
              >
                Mês
              </Button>
              <Button
                variant={view === 'week' ? 'contained' : 'outlined'}
                onClick={() => setView('week')}
                size="small"
              >
                Semana
              </Button>
              <Button
                variant={view === 'day' ? 'contained' : 'outlined'}
                onClick={() => setView('day')}
                size="small"
              >
                Dia
              </Button>
            </Box>
          }
        />
        <CardContent>
          <Box height="600px">
            <Calendar
              localizer={localizer}
              events={events}
              startAccessor="start"
              endAccessor="end"
              view={view}
              onView={(newView) => setView(newView as 'month' | 'week' | 'day')}
              onSelectEvent={handleSelectEvent}
              onSelectSlot={handleSelectSlot}
              selectable
              style={{ height: '100%' }}
              culture="pt-BR"
              messages={{
                next: 'Próximo',
                previous: 'Anterior',
                today: 'Hoje',
                month: 'Mês',
                week: 'Semana',
                day: 'Dia',
                agenda: 'Agenda',
                date: 'Data',
                time: 'Hora',
                event: 'Evento',
                noEventsInRange: 'Nenhum evento neste período',
                showMore: (total) => `+${total} mais`,
              }}
            />
          </Box>
        </CardContent>
      </Card>

      {/* Lista de Agendamentos */}
      <Card sx={{ mt: 3 }}>
        <CardHeader title="Lista de Agendamentos" />
        <CardContent>
          <TableContainer component={Paper}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>Data/Hora</TableCell>
                  <TableCell>Paciente</TableCell>
                  <TableCell>Serviço</TableCell>
                  <TableCell>Presença</TableCell>
                  <TableCell>Pagamento</TableCell>
                  <TableCell>Valor</TableCell>
                  <TableCell align="center">Ações</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {agendamentos.map((agenda) => (
                  <TableRow key={`${agenda.idPaciente}-${agenda.dtConsulta}`}>
                    <TableCell>
                      <Typography variant="subtitle2">
                        {format(new Date(agenda.dtConsulta), 'dd/MM/yyyy HH:mm', { locale: ptBR })}
                      </Typography>
                    </TableCell>
                    <TableCell>{agenda.nomePaciente}</TableCell>
                    <TableCell>{agenda.nomeProduto}</TableCell>
                    <TableCell>
                      <Chip
                        label={agenda.stPresenca ? 'Confirmada' : 'Pendente'}
                        color={agenda.stPresenca ? 'success' : 'warning'}
                        size="small"
                      />
                    </TableCell>
                    <TableCell>
                      <Chip
                        label={getStatusPagamentoDescricao(agenda.stPagamento)}
                        color={getStatusPagamentoColor(agenda.stPagamento) as any}
                        size="small"
                      />
                    </TableCell>
                    <TableCell>
                      {agenda.vlPreco ? (
                        new Intl.NumberFormat('pt-BR', {
                          style: 'currency',
                          currency: 'BRL',
                        }).format(agenda.vlPreco)
                      ) : (
                        'Não definido'
                      )}
                    </TableCell>
                    <TableCell align="center">
                      <IconButton
                        size="small"
                        onClick={() => setAgendaDialog({ open: true, agenda, isEdit: true })}
                        title="Editar"
                      >
                        <EditIcon />
                      </IconButton>
                      {!agenda.stPresenca && (
                        <IconButton
                          size="small"
                          onClick={() => handleConfirmPresenca(agenda)}
                          title="Confirmar Presença"
                          color="success"
                        >
                          <CheckCircleIcon />
                        </IconButton>
                      )}
                      <IconButton
                        size="small"
                        onClick={() => handleDelete(agenda)}
                        title="Excluir"
                        color="error"
                      >
                        <DeleteIcon />
                      </IconButton>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </CardContent>
      </Card>

      {/* Dialog de Agendamento */}
      <Dialog open={agendaDialog.open} onClose={handleCloseDialog} maxWidth="md" fullWidth>
        <DialogTitle>
          {agendaDialog.isEdit ? 'Editar Agendamento' : 'Novo Agendamento'}
        </DialogTitle>
        <DialogContent>
          <AgendaForm
            agenda={agendaDialog.agenda}
            pacientes={pacientes}
            produtos={produtos}
            onSave={(data) => {
              if (agendaDialog.isEdit && agendaDialog.agenda) {
                updateMutation.mutate({
                  idPaciente: agendaDialog.agenda.idPaciente,
                  dtConsulta: agendaDialog.agenda.dtConsulta,
                  data,
                });
              } else {
                createMutation.mutate(data);
              }
            }}
            onClose={handleCloseDialog}
          />
        </DialogContent>
      </Dialog>
    </Box>
  );
};

// Componente de formulário de agendamento
interface AgendaFormProps {
  agenda: AgendaType | null;
  pacientes: Paciente[];
  produtos: Produto[];
  onSave: (data: AgendaFormData) => void;
  onClose: () => void;
}

const AgendaForm: React.FC<AgendaFormProps> = ({ agenda, pacientes, produtos, onSave, onClose }) => {
  const [formData, setFormData] = useState<AgendaFormData>({
    idPaciente: agenda?.idPaciente || 0,
    dtConsulta: agenda?.dtConsulta || '',
    dtConsultaAte: agenda?.dtConsultaAte || '',
    stPresenca: agenda?.stPresenca || false,
    idProduto: agenda?.idProduto || 0,
    dsObs: agenda?.dsObs || '',
    vlPreco: agenda?.vlPreco || 0,
    stPagamento: agenda?.stPagamento || 0,
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSave(formData);
  };

  return (
    <form onSubmit={handleSubmit}>
      <Grid container spacing={2} sx={{ mt: 1 }}>
        <Grid item xs={12} md={6}>
          <Autocomplete
            options={pacientes}
            getOptionLabel={(option) => option.dsNome}
            value={pacientes.find(p => p.idPaciente === formData.idPaciente) || null}
            onChange={(_, value) => setFormData({ ...formData, idPaciente: value?.idPaciente || 0 })}
            renderInput={(params) => (
              <TextField
                {...params}
                label="Paciente *"
                required
              />
            )}
          />
        </Grid>
        <Grid item xs={12} md={6}>
          <FormControl fullWidth required>
            <InputLabel>Serviço</InputLabel>
            <Select
              value={formData.idProduto}
              onChange={(e) => setFormData({ ...formData, idProduto: e.target.value as number })}
            >
              {produtos.map((produto) => (
                <MenuItem key={produto.idProduto} value={produto.idProduto}>
                  {produto.dsNome}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={12} md={6}>
          <TextField
            label="Data/Hora Início"
            type="datetime-local"
            value={formData.dtConsulta}
            onChange={(e) => setFormData({ ...formData, dtConsulta: e.target.value })}
            fullWidth
            required
            InputLabelProps={{ shrink: true }}
          />
        </Grid>
        <Grid item xs={12} md={6}>
          <TextField
            label="Data/Hora Fim"
            type="datetime-local"
            value={formData.dtConsultaAte}
            onChange={(e) => setFormData({ ...formData, dtConsultaAte: e.target.value })}
            fullWidth
            InputLabelProps={{ shrink: true }}
          />
        </Grid>
        <Grid item xs={12} md={6}>
          <TextField
            label="Valor"
            type="number"
            value={formData.vlPreco}
            onChange={(e) => setFormData({ ...formData, vlPreco: parseFloat(e.target.value) || 0 })}
            fullWidth
            inputProps={{ step: 0.01, min: 0 }}
          />
        </Grid>
        <Grid item xs={12} md={6}>
          <FormControl fullWidth>
            <InputLabel>Status Pagamento</InputLabel>
            <Select
              value={formData.stPagamento}
              onChange={(e) => setFormData({ ...formData, stPagamento: e.target.value as number })}
            >
              {STATUS_PAGAMENTO_OPTIONS.map((option) => (
                <MenuItem key={option.value} value={option.value}>
                  {option.label}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={12}>
          <TextField
            label="Observações"
            multiline
            rows={3}
            value={formData.dsObs}
            onChange={(e) => setFormData({ ...formData, dsObs: e.target.value })}
            fullWidth
          />
        </Grid>
        <Grid item xs={12}>
          <FormControlLabel
            control={
              <Radio
                checked={formData.stPresenca}
                onChange={(e) => setFormData({ ...formData, stPresenca: e.target.checked })}
              />
            }
            label="Presença Confirmada"
          />
        </Grid>
      </Grid>
      <DialogActions>
        <Button onClick={onClose}>Cancelar</Button>
        <Button type="submit" variant="contained">
          {agenda ? 'Atualizar' : 'Salvar'}
        </Button>
      </DialogActions>
    </form>
  );
};

export default Agenda;
