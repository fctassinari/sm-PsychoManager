import React, { useEffect } from 'react';
import {
  Box,
  Button,
  Card,
  CardContent,
  CardHeader,
  Grid,
  TextField,
  Typography,
  Toolbar,
  IconButton,
} from '@mui/material';
import {
  Save as SaveIcon,
  ArrowBack as ArrowBackIcon,
  AttachMoney as MoneyIcon,
} from '@mui/icons-material';
import { useForm, Controller } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { useQuery, useMutation, useQueryClient } from 'react-query';
import { useNavigate, useParams } from 'react-router-dom';
import toast from 'react-hot-toast';
import { produtoService } from '../../services/api';
import { ProdutoFormData } from '../../types';

const schema = yup.object({
  dsNome: yup.string().required('Nome é obrigatório').max(100, 'Nome deve ter no máximo 100 caracteres'),
  vlPreco1: yup.number().min(0, 'Preço deve ser maior ou igual a zero'),
  vlPreco2: yup.number().min(0, 'Preço deve ser maior ou igual a zero'),
  vlPreco3: yup.number().min(0, 'Preço deve ser maior ou igual a zero'),
  vlPreco4: yup.number().min(0, 'Preço deve ser maior ou igual a zero'),
});

const ProdutoForm: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const isEdit = id && id !== 'novo';

  const { data: produto, isLoading } = useQuery(
    ['produto', id],
    () => produtoService.buscarPorId(Number(id)).then(res => res.data),
    {
      enabled: !!isEdit,
    }
  );

  const {
    control,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<ProdutoFormData>({
    resolver: yupResolver(schema),
    defaultValues: {
      dsNome: '',
      vlPreco1: 0,
      vlPreco2: 0,
      vlPreco3: 0,
      vlPreco4: 0,
    },
  });

  const createMutation = useMutation(
    (data: ProdutoFormData) => produtoService.criar(data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries('produtos');
        toast.success('Produto criado com sucesso!');
        navigate('/produtos');
      },
      onError: (error: any) => {
        toast.error('Erro ao criar produto: ' + (error.response?.data?.error || error.message));
      },
    }
  );

  const updateMutation = useMutation(
    (data: ProdutoFormData) => produtoService.atualizar(Number(id), data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries('produtos');
        queryClient.invalidateQueries(['produto', id]);
        toast.success('Produto atualizado com sucesso!');
        navigate('/produtos');
      },
      onError: (error: any) => {
        toast.error('Erro ao atualizar produto: ' + (error.response?.data?.error || error.message));
      },
    }
  );

  useEffect(() => {
    if (produto) {
      reset({
        dsNome: produto.dsNome,
        vlPreco1: produto.vlPreco1 || 0,
        vlPreco2: produto.vlPreco2 || 0,
        vlPreco3: produto.vlPreco3 || 0,
        vlPreco4: produto.vlPreco4 || 0,
      });
    }
  }, [produto, reset]);

  const onSubmit = (data: ProdutoFormData) => {
    if (isEdit) {
      updateMutation.mutate(data);
    } else {
      createMutation.mutate(data);
    }
  };

  if (isLoading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
        <Typography>Carregando produto...</Typography>
      </Box>
    );
  }

  return (
    <Box>
      <Toolbar>
        <IconButton onClick={() => navigate('/produtos')} sx={{ mr: 2 }}>
          <ArrowBackIcon />
        </IconButton>
        <Typography variant="h4">
          {isEdit ? 'Editar' : 'Novo'} Produto/Serviço
        </Typography>
      </Toolbar>

      <form onSubmit={handleSubmit(onSubmit)}>
        <Grid container spacing={3}>
          <Grid item xs={12}>
            <Card>
              <CardHeader title="Dados do Produto/Serviço" />
              <CardContent>
                <Grid container spacing={2}>
                  <Grid item xs={12}>
                    <Controller
                      name="dsNome"
                      control={control}
                      render={({ field }) => (
                        <TextField
                          {...field}
                          label="Nome do Produto/Serviço *"
                          fullWidth
                          error={!!errors.dsNome}
                          helperText={errors.dsNome?.message}
                        />
                      )}
                    />
                  </Grid>
                </Grid>
              </CardContent>
            </Card>
          </Grid>

          <Grid item xs={12}>
            <Card>
              <CardHeader title="Preços por Categoria" />
              <CardContent>
                <Grid container spacing={2}>
                  {[
                    { name: 'vlPreco1', label: 'Preço Categoria 1' },
                    { name: 'vlPreco2', label: 'Preço Categoria 2' },
                    { name: 'vlPreco3', label: 'Preço Categoria 3' },
                    { name: 'vlPreco4', label: 'Preço Categoria 4' },
                  ].map(({ name, label }) => (
                    <Grid item xs={12} md={6} key={name}>
                      <Controller
                        name={name as keyof ProdutoFormData}
                        control={control}
                        render={({ field }) => (
                          <TextField
                            {...field}
                            label={label}
                            type="number"
                            fullWidth
                            InputProps={{
                              startAdornment: <MoneyIcon sx={{ mr: 1 }} />,
                            }}
                            inputProps={{
                              step: 0.01,
                              min: 0,
                            }}
                            error={!!errors[name as keyof typeof errors]}
                            helperText={errors[name as keyof typeof errors]?.message}
                            onChange={(e) => field.onChange(parseFloat(e.target.value) || 0)}
                          />
                        )}
                      />
                    </Grid>
                  ))}
                </Grid>
              </CardContent>
            </Card>
          </Grid>

          {/* Botões de Ação */}
          <Grid item xs={12}>
            <Box display="flex" justifyContent="flex-end" gap={2}>
              <Button
                variant="outlined"
                onClick={() => navigate('/produtos')}
              >
                Cancelar
              </Button>
              <Button
                type="submit"
                variant="contained"
                startIcon={<SaveIcon />}
                disabled={createMutation.isLoading || updateMutation.isLoading}
              >
                {createMutation.isLoading || updateMutation.isLoading
                  ? 'Salvando...'
                  : isEdit
                  ? 'Atualizar'
                  : 'Salvar'}
              </Button>
            </Box>
          </Grid>
        </Grid>
      </form>
    </Box>
  );
};

export default ProdutoForm;
