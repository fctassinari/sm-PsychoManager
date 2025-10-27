import React, { useState } from 'react';
import {
  Box,
  Button,
  Card,
  CardContent,
  Chip,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Grid,
  IconButton,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TextField,
  Typography,
  Toolbar,
  InputAdornment,
} from '@mui/material';
import {
  Add as AddIcon,
  Search as SearchIcon,
  Edit as EditIcon,
  Visibility as VisibilityIcon,
  Delete as DeleteIcon,
  Phone as PhoneIcon,
  Email as EmailIcon,
} from '@mui/icons-material';
import { useQuery, useMutation, useQueryClient } from 'react-query';
import { useNavigate } from 'react-router-dom';
import { format } from 'date-fns';
import { ptBR } from 'date-fns/locale';
import toast from 'react-hot-toast';
import { pacienteService } from '../../services/api';
import { Paciente } from '../../types';

const Pacientes: React.FC = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [deleteDialog, setDeleteDialog] = useState<{
    open: boolean;
    paciente: Paciente | null;
  }>({ open: false, paciente: null });

  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const { data: pacientes = [], isLoading } = useQuery(
    ['pacientes', searchTerm],
    () => {
      if (searchTerm.trim()) {
        return pacienteService.buscar(searchTerm).then(res => res.data);
      }
      return pacienteService.listar().then(res => res.data);
    }
  );

  const deleteMutation = useMutation(
    (id: number) => pacienteService.excluir(id),
    {
      onSuccess: () => {
        queryClient.invalidateQueries('pacientes');
        toast.success('Paciente excluído com sucesso!');
        setDeleteDialog({ open: false, paciente: null });
      },
      onError: (error: any) => {
        toast.error('Erro ao excluir paciente: ' + (error.response?.data?.error || error.message));
      },
    }
  );

  const handleDelete = (paciente: Paciente) => {
    setDeleteDialog({ open: true, paciente });
  };

  const confirmDelete = () => {
    if (deleteDialog.paciente) {
      deleteMutation.mutate(deleteDialog.paciente.idPaciente);
    }
  };

  const getEstadocivilDescricao = (estadocivil?: string) => {
    const map: { [key: string]: string } = {
      'S': 'Solteiro',
      'C': 'Casado',
      'E': 'Separado',
      'V': 'Viúvo',
      'D': 'Divorciado',
    };
    return map[estadocivil || ''] || 'Não informado';
  };

  const getCategoriaValorDescricao = (categoria?: string) => {
    return categoria ? `Categoria ${categoria}` : 'Não definida';
  };

  if (isLoading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
        <Typography>Carregando pacientes...</Typography>
      </Box>
    );
  }

  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        Pacientes
      </Typography>

      <Card>
        <CardContent>
          <Toolbar>
            <TextField
              placeholder="Buscar pacientes..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <SearchIcon />
                  </InputAdornment>
                ),
              }}
              sx={{ flexGrow: 1, mr: 2 }}
            />
            <Button
              variant="contained"
              startIcon={<AddIcon />}
              onClick={() => navigate('/pacientes/novo')}
            >
              Novo Paciente
            </Button>
          </Toolbar>

          <TableContainer component={Paper} sx={{ mt: 2 }}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>Nome</TableCell>
                  <TableCell>Email</TableCell>
                  <TableCell>Telefone</TableCell>
                  <TableCell>Data Nascimento</TableCell>
                  <TableCell>Estado Civil</TableCell>
                  <TableCell>Categoria</TableCell>
                  <TableCell>Ficha</TableCell>
                  <TableCell align="center">Ações</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {pacientes.map((paciente) => (
                  <TableRow key={paciente.idPaciente}>
                    <TableCell>
                      <Typography variant="subtitle2">
                        {paciente.dsNome}
                      </Typography>
                    </TableCell>
                    <TableCell>
                      {paciente.dsEmail ? (
                        <Box display="flex" alignItems="center">
                          <EmailIcon fontSize="small" sx={{ mr: 1 }} />
                          {paciente.dsEmail}
                        </Box>
                      ) : (
                        'Não informado'
                      )}
                    </TableCell>
                    <TableCell>
                      {paciente.telefones && paciente.telefones.length > 0 ? (
                        <Box display="flex" alignItems="center">
                          <PhoneIcon fontSize="small" sx={{ mr: 1 }} />
                          {paciente.telefones[0].nrFone}
                          {paciente.telefones.length > 1 && (
                            <Typography variant="caption" sx={{ ml: 1 }}>
                              (+{paciente.telefones.length - 1})
                            </Typography>
                          )}
                        </Box>
                      ) : (
                        'Não informado'
                      )}
                    </TableCell>
                    <TableCell>
                      {paciente.dtNascimento ? (
                        format(new Date(paciente.dtNascimento), 'dd/MM/yyyy', { locale: ptBR })
                      ) : (
                        'Não informado'
                      )}
                    </TableCell>
                    <TableCell>
                      {getEstadocivilDescricao(paciente.stEstadocivil)}
                    </TableCell>
                    <TableCell>
                      <Chip
                        label={getCategoriaValorDescricao(paciente.stCatValor)}
                        size="small"
                        color="primary"
                        variant="outlined"
                      />
                    </TableCell>
                    <TableCell>
                      {paciente.dsFicha ? (
                        <Chip
                          label="PDF"
                          size="small"
                          color="success"
                          variant="outlined"
                        />
                      ) : (
                        <Chip
                          label="Sem ficha"
                          size="small"
                          color="default"
                          variant="outlined"
                        />
                      )}
                    </TableCell>
                    <TableCell align="center">
                      <IconButton
                        size="small"
                        onClick={() => navigate(`/pacientes/visualizar/${paciente.idPaciente}`)}
                        title="Visualizar"
                      >
                        <VisibilityIcon />
                      </IconButton>
                      <IconButton
                        size="small"
                        onClick={() => navigate(`/pacientes/editar/${paciente.idPaciente}`)}
                        title="Editar"
                      >
                        <EditIcon />
                      </IconButton>
                      <IconButton
                        size="small"
                        onClick={() => handleDelete(paciente)}
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

          {pacientes.length === 0 && (
            <Box textAlign="center" py={4}>
              <Typography variant="h6" color="textSecondary">
                {searchTerm ? 'Nenhum paciente encontrado' : 'Nenhum paciente cadastrado'}
              </Typography>
            </Box>
          )}
        </CardContent>
      </Card>

      {/* Dialog de confirmação de exclusão */}
      <Dialog
        open={deleteDialog.open}
        onClose={() => setDeleteDialog({ open: false, paciente: null })}
      >
        <DialogTitle>Confirmar Exclusão</DialogTitle>
        <DialogContent>
          <DialogContentText>
            Tem certeza que deseja excluir o paciente "{deleteDialog.paciente?.dsNome}"?
            Esta ação não pode ser desfeita.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setDeleteDialog({ open: false, paciente: null })}>
            Cancelar
          </Button>
          <Button
            onClick={confirmDelete}
            color="error"
            variant="contained"
            disabled={deleteMutation.isLoading}
          >
            {deleteMutation.isLoading ? 'Excluindo...' : 'Excluir'}
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default Pacientes;
