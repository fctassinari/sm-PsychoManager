import React, { useState } from 'react';
import {
  Box,
  Button,
  Card,
  CardContent,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  IconButton,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TextField,
  Toolbar,
  Typography,
  InputAdornment,
} from '@mui/material';
import {
  Add as AddIcon,
  Search as SearchIcon,
  Edit as EditIcon,
  Delete as DeleteIcon,
  AttachMoney as MoneyIcon,
} from '@mui/icons-material';
import { useQuery, useMutation, useQueryClient } from 'react-query';
import { useNavigate } from 'react-router-dom';
import toast from 'react-hot-toast';
import { produtoService } from '../../services/api';
import { Produto } from '../../types';

const Produtos: React.FC = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [deleteDialog, setDeleteDialog] = useState<{
    open: boolean;
    produto: Produto | null;
  }>({ open: false, produto: null });

  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const { data: produtos = [], isLoading } = useQuery(
    ['produtos', searchTerm],
    () => {
      if (searchTerm.trim()) {
        return produtoService.buscar(searchTerm).then(res => res.data);
      }
      return produtoService.listar().then(res => res.data);
    }
  );

  const deleteMutation = useMutation(
    (id: number) => produtoService.excluir(id),
    {
      onSuccess: () => {
        queryClient.invalidateQueries('produtos');
        toast.success('Produto excluído com sucesso!');
        setDeleteDialog({ open: false, produto: null });
      },
      onError: (error: any) => {
        toast.error('Erro ao excluir produto: ' + (error.response?.data?.error || error.message));
      },
    }
  );

  const handleDelete = (produto: Produto) => {
    setDeleteDialog({ open: true, produto });
  };

  const confirmDelete = () => {
    if (deleteDialog.produto) {
      deleteMutation.mutate(deleteDialog.produto.idProduto);
    }
  };

  const formatCurrency = (value?: number) => {
    if (value === null || value === undefined) return 'Não definido';
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL',
    }).format(value);
  };

  if (isLoading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
        <Typography>Carregando produtos...</Typography>
      </Box>
    );
  }

  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        Produtos/Serviços
      </Typography>

      <Card>
        <CardContent>
          <Toolbar>
            <TextField
              placeholder="Buscar produtos..."
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
              onClick={() => navigate('/produtos/novo')}
            >
              Novo Produto
            </Button>
          </Toolbar>

          <TableContainer component={Paper} sx={{ mt: 2 }}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>Nome</TableCell>
                  <TableCell align="right">Preço 1</TableCell>
                  <TableCell align="right">Preço 2</TableCell>
                  <TableCell align="right">Preço 3</TableCell>
                  <TableCell align="right">Preço 4</TableCell>
                  <TableCell align="center">Ações</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {produtos.map((produto) => (
                  <TableRow key={produto.idProduto}>
                    <TableCell>
                      <Typography variant="subtitle2">
                        {produto.dsNome}
                      </Typography>
                    </TableCell>
                    <TableCell align="right">
                      <Box display="flex" alignItems="center" justifyContent="flex-end">
                        <MoneyIcon fontSize="small" sx={{ mr: 0.5 }} />
                        {formatCurrency(produto.vlPreco1)}
                      </Box>
                    </TableCell>
                    <TableCell align="right">
                      <Box display="flex" alignItems="center" justifyContent="flex-end">
                        <MoneyIcon fontSize="small" sx={{ mr: 0.5 }} />
                        {formatCurrency(produto.vlPreco2)}
                      </Box>
                    </TableCell>
                    <TableCell align="right">
                      <Box display="flex" alignItems="center" justifyContent="flex-end">
                        <MoneyIcon fontSize="small" sx={{ mr: 0.5 }} />
                        {formatCurrency(produto.vlPreco3)}
                      </Box>
                    </TableCell>
                    <TableCell align="right">
                      <Box display="flex" alignItems="center" justifyContent="flex-end">
                        <MoneyIcon fontSize="small" sx={{ mr: 0.5 }} />
                        {formatCurrency(produto.vlPreco4)}
                      </Box>
                    </TableCell>
                    <TableCell align="center">
                      <IconButton
                        size="small"
                        onClick={() => navigate(`/produtos/editar/${produto.idProduto}`)}
                        title="Editar"
                      >
                        <EditIcon />
                      </IconButton>
                      <IconButton
                        size="small"
                        onClick={() => handleDelete(produto)}
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

          {produtos.length === 0 && (
            <Box textAlign="center" py={4}>
              <Typography variant="h6" color="textSecondary">
                {searchTerm ? 'Nenhum produto encontrado' : 'Nenhum produto cadastrado'}
              </Typography>
            </Box>
          )}
        </CardContent>
      </Card>

      {/* Dialog de confirmação de exclusão */}
      <Dialog
        open={deleteDialog.open}
        onClose={() => setDeleteDialog({ open: false, produto: null })}
      >
        <DialogTitle>Confirmar Exclusão</DialogTitle>
        <DialogContent>
          <DialogContentText>
            Tem certeza que deseja excluir o produto "{deleteDialog.produto?.dsNome}"?
            Esta ação não pode ser desfeita.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setDeleteDialog({ open: false, produto: null })}>
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

export default Produtos;
