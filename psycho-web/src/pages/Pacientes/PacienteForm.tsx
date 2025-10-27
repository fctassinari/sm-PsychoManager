import React, { useState, useEffect } from 'react';
import {
  Box,
  Button,
  Card,
  CardContent,
  CardHeader,
  Checkbox,
  FormControl,
  FormControlLabel,
  FormLabel,
  Grid,
  IconButton,
  MenuItem,
  Paper,
  Radio,
  RadioGroup,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TextField,
  Typography,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Toolbar,
} from '@mui/material';
import {
  Add as AddIcon,
  Delete as DeleteIcon,
  Save as SaveIcon,
  ArrowBack as ArrowBackIcon,
} from '@mui/icons-material';
import { useForm, useFieldArray, Controller } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { useQuery, useMutation, useQueryClient } from 'react-query';
import { useNavigate, useParams } from 'react-router-dom';
import toast from 'react-hot-toast';
import { pacienteService } from '../../services/api';
import { Paciente, PacienteFormData, TelefoneFormData } from '../../types';
import {
  ESTADO_CIVIL_OPTIONS,
  TIPO_TELEFONE_OPTIONS,
  CATEGORIA_VALOR_OPTIONS,
  SIM_NAO_OPTIONS,
} from '../../types';

const schema = yup.object({
  dsNome: yup.string().required('Nome é obrigatório').max(100, 'Nome deve ter no máximo 100 caracteres'),
  dsEmail: yup.string().email('Email inválido').max(100, 'Email deve ter no máximo 100 caracteres'),
  dsProfissao: yup.string().max(100, 'Profissão deve ter no máximo 100 caracteres'),
  dsEscolaridade: yup.string().max(100, 'Escolaridade deve ter no máximo 100 caracteres'),
  stEstadocivil: yup.string().oneOf(['S', 'C', 'E', 'V', 'D']),
  dtNascimento: yup.string(),
  dsFilhos: yup.string().max(100, 'Filhos deve ter no máximo 100 caracteres'),
  dsEndereco: yup.string().required('Endereço é obrigatório').max(100, 'Endereço deve ter no máximo 100 caracteres'),
  dsBairro: yup.string().required('Bairro é obrigatório').max(100, 'Bairro deve ter no máximo 100 caracteres'),
  nrCep: yup.string().required('CEP é obrigatório').max(9, 'CEP deve ter no máximo 9 caracteres'),
  dsQueixas: yup.string().max(1000, 'Queixas deve ter no máximo 1000 caracteres'),
  dsProbsaude: yup.string().max(200, 'Problemas de saúde deve ter no máximo 200 caracteres'),
  dsAcompmedico: yup.string().max(100, 'Acompanhamento médico deve ter no máximo 100 caracteres'),
  dsRemedios: yup.string().max(100, 'Remédios deve ter no máximo 100 caracteres'),
  stBebe: yup.string().oneOf(['S', 'N']),
  stFuma: yup.string().oneOf(['S', 'N']),
  stDrogas: yup.string().oneOf(['S', 'N']),
  stInsonia: yup.string().oneOf(['S', 'N']),
  dsCalmante: yup.string().max(100, 'Calmante deve ter no máximo 100 caracteres'),
  stTratpsic: yup.string().oneOf(['S', 'N']),
  dsResultado: yup.string().max(1000, 'Resultado deve ter no máximo 1000 caracteres'),
  dsObservacao: yup.string().max(1000, 'Observação deve ter no máximo 1000 caracteres'),
  dsFicha: yup.string().max(200, 'Ficha deve ter no máximo 200 caracteres'),
  stCatValor: yup.string().oneOf(['1', '2', '3', '4']),
  telefones: yup.array().of(
    yup.object({
      nrFone: yup.string().required('Número é obrigatório').max(17, 'Número deve ter no máximo 17 caracteres'),
      tpFone: yup.string().required('Tipo é obrigatório').oneOf(['C', 'R', 'W']),
    })
  ),
  dsSenha: yup.string().max(8, 'Senha deve ter no máximo 8 caracteres'),
});

const PacienteForm: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const [telefoneDialog, setTelefoneDialog] = useState(false);
  const [novoTelefone, setNovoTelefone] = useState<TelefoneFormData>({
    nrFone: '',
    tpFone: 'C',
  });

  const isEdit = id && id !== 'novo';
  const isView = window.location.pathname.includes('/visualizar/');

  const { data: paciente, isLoading } = useQuery(
    ['paciente', id],
    () => pacienteService.buscarPorId(Number(id)).then(res => res.data),
    {
      enabled: !!isEdit,
    }
  );

  const {
    control,
    handleSubmit,
    reset,
    watch,
    formState: { errors },
  } = useForm<PacienteFormData>({
    resolver: yupResolver(schema) as any,
    defaultValues: {
      telefones: [],
    },
  });

  const { fields, append, remove } = useFieldArray({
    control,
    name: 'telefones',
  });

  const createMutation = useMutation(
    (data: PacienteFormData) => pacienteService.criar(data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries('pacientes');
        toast.success('Paciente criado com sucesso!');
        navigate('/pacientes');
      },
      onError: (error: any) => {
        toast.error('Erro ao criar paciente: ' + (error.response?.data?.error || error.message));
      },
    }
  );

  const updateMutation = useMutation(
    (data: PacienteFormData) => pacienteService.atualizar(Number(id), data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries('pacientes');
        queryClient.invalidateQueries(['paciente', id]);
        toast.success('Paciente atualizado com sucesso!');
        navigate('/pacientes');
      },
      onError: (error: any) => {
        toast.error('Erro ao atualizar paciente: ' + (error.response?.data?.error || error.message));
      },
    }
  );

  useEffect(() => {
    if (paciente) {
      reset({
        ...paciente,
        telefones: paciente.telefones || [],
      });
    }
  }, [paciente, reset]);

  const onSubmit = (data: PacienteFormData) => {
    if (isEdit) {
      updateMutation.mutate(data);
    } else {
      createMutation.mutate(data);
    }
  };

  const handleAddTelefone = () => {
    if (novoTelefone.nrFone.trim()) {
      append(novoTelefone);
      setNovoTelefone({ nrFone: '', tpFone: 'C' });
      setTelefoneDialog(false);
    }
  };

  const handleRemoveTelefone = (index: number) => {
    remove(index);
  };

  if (isLoading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
        <Typography>Carregando paciente...</Typography>
      </Box>
    );
  }

  return (
    <Box>
      <Toolbar>
        <IconButton onClick={() => navigate('/pacientes')} sx={{ mr: 2 }}>
          <ArrowBackIcon />
        </IconButton>
        <Typography variant="h4">
          {isEdit ? (isView ? 'Visualizar' : 'Editar') : 'Novo'} Paciente
        </Typography>
      </Toolbar>

      <form onSubmit={handleSubmit(onSubmit)}>
        <Grid container spacing={3}>
          {/* Dados Pessoais */}
          <Grid item xs={12}>
            <Card>
              <CardHeader title="Dados Pessoais" />
              <CardContent>
                <Grid container spacing={2}>
                  <Grid item xs={12} md={6}>
                    <Controller
                      name="dsNome"
                      control={control}
                      render={({ field }) => (
                        <TextField
                          {...field}
                          label="Nome *"
                          fullWidth
                          error={!!errors.dsNome}
                          helperText={errors.dsNome?.message}
                          disabled={isView}
                        />
                      )}
                    />
                  </Grid>
                  <Grid item xs={12} md={6}>
                    <Controller
                      name="dsEmail"
                      control={control}
                      render={({ field }) => (
                        <TextField
                          {...field}
                          label="Email"
                          type="email"
                          fullWidth
                          error={!!errors.dsEmail}
                          helperText={errors.dsEmail?.message}
                          disabled={isView}
                        />
                      )}
                    />
                  </Grid>
                  <Grid item xs={12} md={6}>
                    <Controller
                      name="dsProfissao"
                      control={control}
                      render={({ field }) => (
                        <TextField
                          {...field}
                          label="Profissão"
                          fullWidth
                          error={!!errors.dsProfissao}
                          helperText={errors.dsProfissao?.message}
                          disabled={isView}
                        />
                      )}
                    />
                  </Grid>
                  <Grid item xs={12} md={6}>
                    <Controller
                      name="dsEscolaridade"
                      control={control}
                      render={({ field }) => (
                        <TextField
                          {...field}
                          label="Escolaridade"
                          fullWidth
                          error={!!errors.dsEscolaridade}
                          helperText={errors.dsEscolaridade?.message}
                          disabled={isView}
                        />
                      )}
                    />
                  </Grid>
                  <Grid item xs={12} md={6}>
                    <Controller
                      name="dtNascimento"
                      control={control}
                      render={({ field }) => (
                        <TextField
                          {...field}
                          label="Data de Nascimento"
                          type="date"
                          InputLabelProps={{ shrink: true }}
                          fullWidth
                          error={!!errors.dtNascimento}
                          helperText={errors.dtNascimento?.message}
                          disabled={isView}
                        />
                      )}
                    />
                  </Grid>
                  <Grid item xs={12} md={6}>
                    <Controller
                      name="stEstadocivil"
                      control={control}
                      render={({ field }) => (
                        <FormControl fullWidth error={!!errors.stEstadocivil}>
                          <FormLabel>Estado Civil</FormLabel>
                          <RadioGroup {...field} row>
                            {ESTADO_CIVIL_OPTIONS.map((option) => (
                              <FormControlLabel
                                key={option.value}
                                value={option.value}
                                control={<Radio disabled={isView} />}
                                label={option.label}
                              />
                            ))}
                          </RadioGroup>
                        </FormControl>
                      )}
                    />
                  </Grid>
                  <Grid item xs={12} md={6}>
                    <Controller
                      name="dsFilhos"
                      control={control}
                      render={({ field }) => (
                        <TextField
                          {...field}
                          label="Filhos"
                          fullWidth
                          error={!!errors.dsFilhos}
                          helperText={errors.dsFilhos?.message}
                          disabled={isView}
                        />
                      )}
                    />
                  </Grid>
                  <Grid item xs={12} md={6}>
                    <Controller
                      name="stCatValor"
                      control={control}
                      render={({ field }) => (
                        <FormControl fullWidth error={!!errors.stCatValor}>
                          <FormLabel>Categoria de Valor *</FormLabel>
                          <RadioGroup {...field} row>
                            {CATEGORIA_VALOR_OPTIONS.map((option) => (
                              <FormControlLabel
                                key={option.value}
                                value={option.value}
                                control={<Radio disabled={isView} />}
                                label={option.label}
                              />
                            ))}
                          </RadioGroup>
                        </FormControl>
                      )}
                    />
                  </Grid>
                </Grid>
              </CardContent>
            </Card>
          </Grid>

          {/* Telefones */}
          <Grid item xs={12}>
            <Card>
              <CardHeader 
                title="Telefones"
                action={
                  !isView && (
                    <Button
                      startIcon={<AddIcon />}
                      onClick={() => setTelefoneDialog(true)}
                    >
                      Adicionar
                    </Button>
                  )
                }
              />
              <CardContent>
                <TableContainer component={Paper}>
                  <Table>
                    <TableHead>
                      <TableRow>
                        <TableCell>Tipo</TableCell>
                        <TableCell>Número</TableCell>
                        {!isView && <TableCell align="center">Ações</TableCell>}
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {fields.map((field, index) => (
                        <TableRow key={field.id}>
                          <TableCell>
                            {TIPO_TELEFONE_OPTIONS.find(opt => opt.value === watch(`telefones.${index}.tpFone`))?.label}
                          </TableCell>
                          <TableCell>{watch(`telefones.${index}.nrFone`)}</TableCell>
                          {!isView && (
                            <TableCell align="center">
                              <IconButton
                                size="small"
                                onClick={() => handleRemoveTelefone(index)}
                                color="error"
                              >
                                <DeleteIcon />
                              </IconButton>
                            </TableCell>
                          )}
                        </TableRow>
                      ))}
                    </TableBody>
                  </Table>
                </TableContainer>
              </CardContent>
            </Card>
          </Grid>

          {/* Endereço */}
          <Grid item xs={12}>
            <Card>
              <CardHeader title="Endereço" />
              <CardContent>
                <Grid container spacing={2}>
                  <Grid item xs={12} md={8}>
                    <Controller
                      name="dsEndereco"
                      control={control}
                      render={({ field }) => (
                        <TextField
                          {...field}
                          label="Endereço *"
                          fullWidth
                          error={!!errors.dsEndereco}
                          helperText={errors.dsEndereco?.message}
                          disabled={isView}
                        />
                      )}
                    />
                  </Grid>
                  <Grid item xs={12} md={4}>
                    <Controller
                      name="dsBairro"
                      control={control}
                      render={({ field }) => (
                        <TextField
                          {...field}
                          label="Bairro *"
                          fullWidth
                          error={!!errors.dsBairro}
                          helperText={errors.dsBairro?.message}
                          disabled={isView}
                        />
                      )}
                    />
                  </Grid>
                  <Grid item xs={12} md={4}>
                    <Controller
                      name="nrCep"
                      control={control}
                      render={({ field }) => (
                        <TextField
                          {...field}
                          label="CEP *"
                          fullWidth
                          error={!!errors.nrCep}
                          helperText={errors.nrCep?.message}
                          disabled={isView}
                        />
                      )}
                    />
                  </Grid>
                </Grid>
              </CardContent>
            </Card>
          </Grid>

          {/* Dados Médicos */}
          <Grid item xs={12}>
            <Card>
              <CardHeader title="Dados Médicos" />
              <CardContent>
                <Grid container spacing={2}>
                  <Grid item xs={12}>
                    <Controller
                      name="dsQueixas"
                      control={control}
                      render={({ field }) => (
                        <TextField
                          {...field}
                          label="Queixas"
                          multiline
                          rows={3}
                          fullWidth
                          error={!!errors.dsQueixas}
                          helperText={errors.dsQueixas?.message}
                          disabled={isView}
                        />
                      )}
                    />
                  </Grid>
                  <Grid item xs={12} md={6}>
                    <Controller
                      name="dsProbsaude"
                      control={control}
                      render={({ field }) => (
                        <TextField
                          {...field}
                          label="Problemas de Saúde"
                          multiline
                          rows={2}
                          fullWidth
                          error={!!errors.dsProbsaude}
                          helperText={errors.dsProbsaude?.message}
                          disabled={isView}
                        />
                      )}
                    />
                  </Grid>
                  <Grid item xs={12} md={6}>
                    <Controller
                      name="dsAcompmedico"
                      control={control}
                      render={({ field }) => (
                        <TextField
                          {...field}
                          label="Acompanhamento Médico"
                          fullWidth
                          error={!!errors.dsAcompmedico}
                          helperText={errors.dsAcompmedico?.message}
                          disabled={isView}
                        />
                      )}
                    />
                  </Grid>
                  <Grid item xs={12} md={6}>
                    <Controller
                      name="dsRemedios"
                      control={control}
                      render={({ field }) => (
                        <TextField
                          {...field}
                          label="Remédios"
                          fullWidth
                          error={!!errors.dsRemedios}
                          helperText={errors.dsRemedios?.message}
                          disabled={isView}
                        />
                      )}
                    />
                  </Grid>
                  <Grid item xs={12} md={6}>
                    <Controller
                      name="dsCalmante"
                      control={control}
                      render={({ field }) => (
                        <TextField
                          {...field}
                          label="Calmante"
                          fullWidth
                          error={!!errors.dsCalmante}
                          helperText={errors.dsCalmante?.message}
                          disabled={isView}
                        />
                      )}
                    />
                  </Grid>
                </Grid>
              </CardContent>
            </Card>
          </Grid>

          {/* Hábitos */}
          <Grid item xs={12}>
            <Card>
              <CardHeader title="Hábitos" />
              <CardContent>
                <Grid container spacing={2}>
                  {[
                    { name: 'stBebe', label: 'Bebe' },
                    { name: 'stFuma', label: 'Fuma' },
                    { name: 'stDrogas', label: 'Usa Drogas' },
                    { name: 'stInsonia', label: 'Insônia' },
                    { name: 'stTratpsic', label: 'Tratamento Psicológico' },
                  ].map(({ name, label }) => (
                    <Grid item xs={12} md={6} key={name}>
                      <Controller
                        name={name as keyof PacienteFormData}
                        control={control}
                        render={({ field }) => (
                          <FormControl fullWidth>
                            <FormLabel>{label}</FormLabel>
                            <RadioGroup {...field} row>
                              {SIM_NAO_OPTIONS.map((option) => (
                                <FormControlLabel
                                  key={option.value}
                                  value={option.value}
                                  control={<Radio disabled={isView} />}
                                  label={option.label}
                                />
                              ))}
                            </RadioGroup>
                          </FormControl>
                        )}
                      />
                    </Grid>
                  ))}
                </Grid>
              </CardContent>
            </Card>
          </Grid>

          {/* Observações */}
          <Grid item xs={12}>
            <Card>
              <CardHeader title="Observações" />
              <CardContent>
                <Grid container spacing={2}>
                  <Grid item xs={12} md={6}>
                    <Controller
                      name="dsResultado"
                      control={control}
                      render={({ field }) => (
                        <TextField
                          {...field}
                          label="Resultado"
                          multiline
                          rows={3}
                          fullWidth
                          error={!!errors.dsResultado}
                          helperText={errors.dsResultado?.message}
                          disabled={isView}
                        />
                      )}
                    />
                  </Grid>
                  <Grid item xs={12} md={6}>
                    <Controller
                      name="dsObservacao"
                      control={control}
                      render={({ field }) => (
                        <TextField
                          {...field}
                          label="Observação"
                          multiline
                          rows={3}
                          fullWidth
                          error={!!errors.dsObservacao}
                          helperText={errors.dsObservacao?.message}
                          disabled={isView}
                        />
                      )}
                    />
                  </Grid>
                  <Grid item xs={12} md={6}>
                    <Controller
                      name="dsFicha"
                      control={control}
                      render={({ field }) => (
                        <TextField
                          {...field}
                          label="Ficha"
                          fullWidth
                          error={!!errors.dsFicha}
                          helperText={errors.dsFicha?.message}
                          disabled={isView}
                        />
                      )}
                    />
                  </Grid>
                  <Grid item xs={12} md={6}>
                    <Controller
                      name="dsSenha"
                      control={control}
                      render={({ field }) => (
                        <TextField
                          {...field}
                          label="Senha de Acesso"
                          type="password"
                          fullWidth
                          error={!!errors.dsSenha}
                          helperText={errors.dsSenha?.message}
                          disabled={isView}
                        />
                      )}
                    />
                  </Grid>
                </Grid>
              </CardContent>
            </Card>
          </Grid>

          {/* Botões de Ação */}
          {!isView && (
            <Grid item xs={12}>
              <Box display="flex" justifyContent="flex-end" gap={2}>
                <Button
                  variant="outlined"
                  onClick={() => navigate('/pacientes')}
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
          )}
        </Grid>
      </form>

      {/* Dialog para adicionar telefone */}
      <Dialog open={telefoneDialog} onClose={() => setTelefoneDialog(false)}>
        <DialogTitle>Adicionar Telefone</DialogTitle>
        <DialogContent>
          <Grid container spacing={2} sx={{ mt: 1 }}>
            <Grid item xs={12} md={6}>
              <TextField
                select
                label="Tipo"
                value={novoTelefone.tpFone}
                onChange={(e) => setNovoTelefone({ ...novoTelefone, tpFone: e.target.value as 'C' | 'R' | 'W' })}
                fullWidth
              >
                {TIPO_TELEFONE_OPTIONS.map((option) => (
                  <MenuItem key={option.value} value={option.value}>
                    {option.label}
                  </MenuItem>
                ))}
              </TextField>
            </Grid>
            <Grid item xs={12} md={6}>
              <TextField
                label="Número"
                value={novoTelefone.nrFone}
                onChange={(e) => setNovoTelefone({ ...novoTelefone, nrFone: e.target.value })}
                fullWidth
                placeholder="(11) 99999-9999"
              />
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setTelefoneDialog(false)}>Cancelar</Button>
          <Button onClick={handleAddTelefone} variant="contained">
            Adicionar
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default PacienteForm;
