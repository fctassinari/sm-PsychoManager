import React, { useState } from 'react';
import {
  Box,
  Button,
  Card,
  CardContent,
  CardHeader,
  FormControl,
  Grid,
  InputLabel,
  MenuItem,
  Paper,
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
  Chip,
  Divider,
} from '@mui/material';
import {
  Assessment as AssessmentIcon,
  Download as DownloadIcon,
  Search as SearchIcon,
} from '@mui/icons-material';
import { useQuery } from 'react-query';
import { format } from 'date-fns';
import { ptBR } from 'date-fns/locale';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, PieChart, Pie, Cell } from 'recharts';
import { agendaService, pacienteService, produtoService } from '../../services/api';
import { Agenda as AgendaType, Paciente, Produto } from '../../types';

const Relatorios: React.FC = () => {
  const [filters, setFilters] = useState({
    dataInicio: format(new Date(new Date().getFullYear(), 0, 1), 'yyyy-MM-dd'),
    dataFim: format(new Date(), 'yyyy-MM-dd'),
    idPaciente: '',
    idProduto: '',
    stPresenca: '',
    stPagamento: '',
  });

  const { data: agendamentos = [] } = useQuery(
    ['relatorios', filters],
    () => {
      if (filters.dataInicio && filters.dataFim) {
        return agendaService.buscarConsultasRealizadasPorPeriodo(filters.dataInicio, filters.dataFim).then(res => res.data);
      }
      return Promise.resolve([]);
    }
  );

  const { data: pacientes = [] } = useQuery('pacientes', () =>
    pacienteService.listar().then(res => res.data)
  );

  const { data: produtos = [] } = useQuery('produtos', () =>
    produtoService.listar().then(res => res.data)
  );

  // Processar dados para gráficos
  const dadosPorProduto = agendamentos.reduce((acc: any[], agenda: AgendaType) => {
    const produto = produtos.find(p => p.idProduto === agenda.idProduto);
    const nomeProduto = produto?.dsNome || 'Desconhecido';
    const existing = acc.find(item => item.nome === nomeProduto);
    if (existing) {
      existing.quantidade += 1;
      existing.valor += agenda.vlPreco || 0;
    } else {
      acc.push({
        nome: nomeProduto,
        quantidade: 1,
        valor: agenda.vlPreco || 0,
      });
    }
    return acc;
  }, []);

  const dadosPorPagamento = agendamentos.reduce((acc: any[], agenda: AgendaType) => {
    const status = getStatusPagamentoDescricao(agenda.stPagamento);
    const existing = acc.find(item => item.status === status);
    if (existing) {
      existing.quantidade += 1;
      existing.valor += agenda.vlPreco || 0;
    } else {
      acc.push({
        status,
        quantidade: 1,
        valor: agenda.vlPreco || 0,
      });
    }
    return acc;
  }, []);

  const dadosPorMes = agendamentos.reduce((acc: any[], agenda: AgendaType) => {
    const mes = format(new Date(agenda.dtConsulta), 'MMM/yyyy', { locale: ptBR });
    const existing = acc.find(item => item.mes === mes);
    if (existing) {
      existing.quantidade += 1;
      existing.valor += agenda.vlPreco || 0;
    } else {
      acc.push({
        mes,
        quantidade: 1,
        valor: agenda.vlPreco || 0,
      });
    }
    return acc;
  }, []).sort((a, b) => {
    const dateA = new Date(a.mes.split('/')[1], a.mes.split('/')[0] - 1);
    const dateB = new Date(b.mes.split('/')[1], b.mes.split('/')[0] - 1);
    return dateA.getTime() - dateB.getTime();
  });

  const totalConsultas = agendamentos.length;
  const totalValor = agendamentos.reduce((sum, agenda) => sum + (agenda.vlPreco || 0), 0);
  const consultasConfirmadas = agendamentos.filter(a => a.stPresenca).length;
  const consultasPendentes = agendamentos.filter(a => !a.stPresenca).length;

  const getStatusPagamentoDescricao = (status: number) => {
    const statusMap = {
      0: 'A Receber',
      1: 'Pago',
      2: 'Abonado',
      3: 'Calote',
    };
    return statusMap[status as keyof typeof statusMap] || 'Desconhecido';
  };

  const getStatusPagamentoColor = (status: number) => {
    const colors = {
      0: '#ff9800',
      1: '#4caf50',
      2: '#ffc107',
      3: '#f44336',
    };
    return colors[status as keyof typeof colors] || '#9e9e9e';
  };

  const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042', '#8884D8'];

  const handleExport = () => {
    // Implementar exportação de dados
    console.log('Exportar dados:', agendamentos);
  };

  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        Relatórios
      </Typography>

      {/* Filtros */}
      <Card sx={{ mb: 3 }}>
        <CardHeader title="Filtros" />
        <CardContent>
          <Grid container spacing={2}>
            <Grid item xs={12} md={3}>
              <TextField
                label="Data Início"
                type="date"
                value={filters.dataInicio}
                onChange={(e) => setFilters({ ...filters, dataInicio: e.target.value })}
                fullWidth
                InputLabelProps={{ shrink: true }}
              />
            </Grid>
            <Grid item xs={12} md={3}>
              <TextField
                label="Data Fim"
                type="date"
                value={filters.dataFim}
                onChange={(e) => setFilters({ ...filters, dataFim: e.target.value })}
                fullWidth
                InputLabelProps={{ shrink: true }}
              />
            </Grid>
            <Grid item xs={12} md={3}>
              <Autocomplete
                options={pacientes}
                getOptionLabel={(option) => option.dsNome}
                value={pacientes.find(p => p.idPaciente.toString() === filters.idPaciente) || null}
                onChange={(_, value) => setFilters({ ...filters, idPaciente: value?.idPaciente.toString() || '' })}
                renderInput={(params) => (
                  <TextField
                    {...params}
                    label="Paciente"
                    placeholder="Todos os pacientes"
                  />
                )}
              />
            </Grid>
            <Grid item xs={12} md={3}>
              <FormControl fullWidth>
                <InputLabel>Serviço</InputLabel>
                <Select
                  value={filters.idProduto}
                  onChange={(e) => setFilters({ ...filters, idProduto: e.target.value })}
                >
                  <MenuItem value="">Todos os serviços</MenuItem>
                  {produtos.map((produto) => (
                    <MenuItem key={produto.idProduto} value={produto.idProduto.toString()}>
                      {produto.dsNome}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Grid>
          </Grid>
        </CardContent>
      </Card>

      {/* Resumo */}
      <Grid container spacing={3} sx={{ mb: 3 }}>
        <Grid item xs={12} sm={6} md={3}>
          <Card>
            <CardContent>
              <Box display="flex" alignItems="center">
                <AssessmentIcon color="primary" sx={{ mr: 2, fontSize: 40 }} />
                <Box>
                  <Typography variant="h4">{totalConsultas}</Typography>
                  <Typography color="textSecondary">Total de Consultas</Typography>
                </Box>
              </Box>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <Card>
            <CardContent>
              <Box display="flex" alignItems="center">
                <AssessmentIcon color="success" sx={{ mr: 2, fontSize: 40 }} />
                <Box>
                  <Typography variant="h4">
                    {new Intl.NumberFormat('pt-BR', {
                      style: 'currency',
                      currency: 'BRL',
                    }).format(totalValor)}
                  </Typography>
                  <Typography color="textSecondary">Valor Total</Typography>
                </Box>
              </Box>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <Card>
            <CardContent>
              <Box display="flex" alignItems="center">
                <AssessmentIcon color="success" sx={{ mr: 2, fontSize: 40 }} />
                <Box>
                  <Typography variant="h4">{consultasConfirmadas}</Typography>
                  <Typography color="textSecondary">Confirmadas</Typography>
                </Box>
              </Box>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <Card>
            <CardContent>
              <Box display="flex" alignItems="center">
                <AssessmentIcon color="warning" sx={{ mr: 2, fontSize: 40 }} />
                <Box>
                  <Typography variant="h4">{consultasPendentes}</Typography>
                  <Typography color="textSecondary">Pendentes</Typography>
                </Box>
              </Box>
            </CardContent>
          </Card>
        </Grid>
      </Grid>

      {/* Gráficos */}
      <Grid container spacing={3}>
        <Grid item xs={12} md={6}>
          <Card>
            <CardHeader title="Consultas por Serviço" />
            <CardContent>
              <ResponsiveContainer width="100%" height={300}>
                <BarChart data={dadosPorProduto}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="nome" />
                  <YAxis />
                  <Tooltip />
                  <Bar dataKey="quantidade" fill="#8884d8" />
                </BarChart>
              </ResponsiveContainer>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} md={6}>
          <Card>
            <CardHeader title="Status de Pagamento" />
            <CardContent>
              <ResponsiveContainer width="100%" height={300}>
                <PieChart>
                  <Pie
                    data={dadosPorPagamento}
                    cx="50%"
                    cy="50%"
                    labelLine={false}
                    label={({ status, quantidade }) => `${status}: ${quantidade}`}
                    outerRadius={80}
                    fill="#8884d8"
                    dataKey="quantidade"
                  >
                    {dadosPorPagamento.map((entry, index) => (
                      <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                    ))}
                  </Pie>
                  <Tooltip />
                </PieChart>
              </ResponsiveContainer>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12}>
          <Card>
            <CardHeader title="Evolução Mensal" />
            <CardContent>
              <ResponsiveContainer width="100%" height={300}>
                <BarChart data={dadosPorMes}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="mes" />
                  <YAxis />
                  <Tooltip />
                  <Bar dataKey="quantidade" fill="#82ca9d" />
                </BarChart>
              </ResponsiveContainer>
            </CardContent>
          </Card>
        </Grid>
      </Grid>

      {/* Tabela de Dados */}
      <Card sx={{ mt: 3 }}>
        <CardHeader
          title="Dados Detalhados"
          action={
            <Button
              variant="contained"
              startIcon={<DownloadIcon />}
              onClick={handleExport}
            >
              Exportar
            </Button>
          }
        />
        <CardContent>
          <TableContainer component={Paper}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>Data</TableCell>
                  <TableCell>Paciente</TableCell>
                  <TableCell>Serviço</TableCell>
                  <TableCell>Presença</TableCell>
                  <TableCell>Pagamento</TableCell>
                  <TableCell>Valor</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {agendamentos.map((agenda) => (
                  <TableRow key={`${agenda.idPaciente}-${agenda.dtConsulta}`}>
                    <TableCell>
                      {format(new Date(agenda.dtConsulta), 'dd/MM/yyyy HH:mm', { locale: ptBR })}
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
                        sx={{
                          backgroundColor: getStatusPagamentoColor(agenda.stPagamento),
                          color: 'white',
                        }}
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
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </CardContent>
      </Card>
    </Box>
  );
};

export default Relatorios;
