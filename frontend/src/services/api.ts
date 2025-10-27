import axios from 'axios';
import { Paciente, Produto, Agenda, PacienteFormData, ProdutoFormData, AgendaFormData } from '../types';

const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Interceptor para tratamento de erros
api.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error('API Error:', error);
    return Promise.reject(error);
  }
);

// Serviços de Pacientes
export const pacienteService = {
  listar: () => api.get<Paciente[]>('/pacientes'),
  buscar: (nome: string) => api.get<Paciente[]>(`/pacientes/buscar?nome=${encodeURIComponent(nome)}`),
  buscarPorId: (id: number) => api.get<Paciente>(`/pacientes/${id}`),
  criar: (data: PacienteFormData) => api.post<Paciente>('/pacientes', data),
  atualizar: (id: number, data: PacienteFormData) => api.put<Paciente>(`/pacientes/${id}`, data),
  excluir: (id: number) => api.delete(`/pacientes/${id}`),
  buscarPorEmail: (email: string) => api.get<Paciente[]>(`/pacientes/email/${email}`),
};

// Serviços de Produtos
export const produtoService = {
  listar: () => api.get<Produto[]>('/produtos'),
  buscar: (nome: string) => api.get<Produto[]>(`/produtos/buscar?nome=${encodeURIComponent(nome)}`),
  buscarPorId: (id: number) => api.get<Produto>(`/produtos/${id}`),
  criar: (data: ProdutoFormData) => api.post<Produto>('/produtos', data),
  atualizar: (id: number, data: ProdutoFormData) => api.put<Produto>(`/produtos/${id}`, data),
  excluir: (id: number) => api.delete(`/produtos/${id}`),
  buscarPrecoPorCategoria: (id: number, categoria: number) => 
    api.get<{ preco: number }>(`/produtos/${id}/preco/${categoria}`),
};

// Serviços de Agenda
export const agendaService = {
  listar: () => api.get<Agenda[]>('/agenda'),
  buscarPorPaciente: (idPaciente: number) => api.get<Agenda[]>(`/agenda/paciente/${idPaciente}`),
  buscarPorPeriodo: (dataInicio: string, dataFim: string) => 
    api.get<Agenda[]>(`/agenda/periodo?de=${dataInicio}&ate=${dataFim}`),
  buscarConsultasEmAbertoPorPeriodo: (dataInicio: string, dataFim: string) => 
    api.get<Agenda[]>(`/agenda/periodo/aberto?de=${dataInicio}&ate=${dataFim}`),
  buscarConsultasEmAbertoPorPaciente: (idPaciente: number) => 
    api.get<Agenda[]>(`/agenda/paciente/${idPaciente}/aberto`),
  buscarPresencasNaoConfirmadas: (idPaciente: number) => 
    api.get<Agenda[]>(`/agenda/paciente/${idPaciente}/nao-confirmadas`),
  buscarConsultasRealizadasPorPeriodo: (dataInicio: string, dataFim: string) => 
    api.get<Agenda[]>(`/agenda/realizadas/periodo?de=${dataInicio}&ate=${dataFim}`),
  buscarConsultasRealizadasPorPaciente: (idPaciente: number, dataInicio: string, dataFim: string) => 
    api.get<Agenda[]>(`/agenda/realizadas/paciente/${idPaciente}?de=${dataInicio}&ate=${dataFim}`),
  criar: (data: AgendaFormData) => api.post<Agenda>('/agenda', data),
  atualizar: (idPaciente: number, dtConsulta: string, data: AgendaFormData) => 
    api.put<Agenda>(`/agenda/${idPaciente}/${dtConsulta}`, data),
  excluir: (idPaciente: number, dtConsulta: string) => 
    api.delete(`/agenda/${idPaciente}/${dtConsulta}`),
  confirmarPresenca: (idPaciente: number, dtConsulta: string) => 
    api.put<Agenda>(`/agenda/${idPaciente}/${dtConsulta}/confirmar-presenca`),
  atualizarPagamento: (idPaciente: number, dtConsulta: string, status: number) => 
    api.put<Agenda>(`/agenda/${idPaciente}/${dtConsulta}/pagamento?status=${status}`),
};

export default api;
