import React from 'react';
import {
  Box,
  Card,
  CardContent,
  Grid,
  Typography,
  Paper,
  List,
  ListItem,
  ListItemText,
  ListItemIcon,
  Divider,
} from '@mui/material';
import {
  People as PeopleIcon,
  Business as BusinessIcon,
  CalendarToday as CalendarIcon,
  Assessment as AssessmentIcon,
  CheckCircle as CheckCircleIcon,
  Schedule as ScheduleIcon,
} from '@mui/icons-material';
import { useQuery } from 'react-query';
import { pacienteService, produtoService, agendaService } from '../../services/api';

const Home: React.FC = () => {
  const { data: pacientes = [] } = useQuery('pacientes', () => 
    pacienteService.listar().then(res => res.data || [])
  );

  const { data: produtos = [] } = useQuery('produtos', () => 
    produtoService.listar().then(res => res.data || [])
  );

  const { data: agendamentos = [] } = useQuery('agendamentos', () => 
    agendaService.listar().then(res => res.data || [])
  );

  const hoje = new Date().toISOString().split('T')[0];
  const agendamentosHoje = (agendamentos || []).filter(ag => 
    ag.dtConsulta.startsWith(hoje)
  );

  const consultasRealizadas = (agendamentos || []).filter(ag => ag.stPresenca === true);
  const consultasPendentes = (agendamentos || []).filter(ag => 
    ag.stPresenca === false && new Date(ag.dtConsulta) >= new Date()
  );

  const stats = [
    {
      title: 'Total de Pacientes',
      value: pacientes.length,
      icon: <PeopleIcon />,
      color: '#1976d2',
    },
    {
      title: 'Produtos/Serviços',
      value: produtos.length,
      icon: <BusinessIcon />,
      color: '#388e3c',
    },
    {
      title: 'Consultas Hoje',
      value: agendamentosHoje.length,
      icon: <CalendarIcon />,
      color: '#f57c00',
    },
    {
      title: 'Consultas Realizadas',
      value: consultasRealizadas.length,
      icon: <CheckCircleIcon />,
      color: '#2e7d32',
    },
  ];

  const recentActivities = [
    {
      title: 'Consultas Pendentes',
      count: consultasPendentes.length,
      icon: <ScheduleIcon />,
    },
    {
      title: 'Total de Agendamentos',
      count: agendamentos.length,
      icon: <CalendarIcon />,
    },
  ];

  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        Dashboard
      </Typography>
      
      <Grid container spacing={3}>
        {/* Estatísticas principais */}
        {stats.map((stat, index) => (
          <Grid item xs={12} sm={6} md={3} key={index}>
            <Card>
              <CardContent>
                <Box display="flex" alignItems="center">
                  <Box
                    sx={{
                      backgroundColor: stat.color,
                      color: 'white',
                      borderRadius: '50%',
                      p: 1,
                      mr: 2,
                    }}
                  >
                    {stat.icon}
                  </Box>
                  <Box>
                    <Typography variant="h4" component="div">
                      {stat.value}
                    </Typography>
                    <Typography color="textSecondary">
                      {stat.title}
                    </Typography>
                  </Box>
                </Box>
              </CardContent>
            </Card>
          </Grid>
        ))}

        {/* Atividades recentes */}
        <Grid item xs={12} md={6}>
          <Paper sx={{ p: 2 }}>
            <Typography variant="h6" gutterBottom>
              Atividades Recentes
            </Typography>
            <List>
              {recentActivities.map((activity, index) => (
                <React.Fragment key={index}>
                  <ListItem>
                    <ListItemIcon>{activity.icon}</ListItemIcon>
                    <ListItemText
                      primary={activity.title}
                      secondary={`${activity.count} registros`}
                    />
                  </ListItem>
                  {index < recentActivities.length - 1 && <Divider />}
                </React.Fragment>
              ))}
            </List>
          </Paper>
        </Grid>

        {/* Próximas consultas */}
        <Grid item xs={12} md={6}>
          <Paper sx={{ p: 2 }}>
            <Typography variant="h6" gutterBottom>
              Próximas Consultas
            </Typography>
            {agendamentosHoje.length > 0 ? (
              <List>
                {agendamentosHoje.slice(0, 5).map((agendamento, index) => (
                  <React.Fragment key={index}>
                    <ListItem>
                      <ListItemText
                        primary={agendamento.nomePaciente}
                        secondary={`${new Date(agendamento.dtConsulta).toLocaleTimeString('pt-BR', {
                          hour: '2-digit',
                          minute: '2-digit',
                        })} - ${agendamento.nomeProduto}`}
                      />
                    </ListItem>
                    {index < agendamentosHoje.length - 1 && <Divider />}
                  </React.Fragment>
                ))}
              </List>
            ) : (
              <Typography color="textSecondary">
                Nenhuma consulta agendada para hoje
              </Typography>
            )}
          </Paper>
        </Grid>
      </Grid>
    </Box>
  );
};

export default Home;
