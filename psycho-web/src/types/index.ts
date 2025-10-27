export interface Paciente {
  idPaciente: number;
  dsNome: string;
  dsEmail?: string;
  dsProfissao?: string;
  dsEscolaridade?: string;
  stEstadocivil?: 'S' | 'C' | 'E' | 'V' | 'D';
  dtNascimento?: string;
  dsFilhos?: string;
  dsEndereco: string;
  dsBairro: string;
  nrCep: string;
  dsQueixas?: string;
  dsProbsaude?: string;
  dsAcompmedico?: string;
  dsRemedios?: string;
  stBebe?: 'S' | 'N';
  stFuma?: 'S' | 'N';
  stDrogas?: 'S' | 'N';
  stInsonia?: 'S' | 'N';
  dsCalmante?: string;
  stTratpsic?: 'S' | 'N';
  dsResultado?: string;
  dsObservacao?: string;
  dsFicha?: string;
  stCatValor?: '1' | '2' | '3' | '4';
  telefones: Telefone[];
  dsSenha?: string;
}

export interface Telefone {
  idFone?: number;
  nrFone: string;
  tpFone: 'C' | 'R' | 'W';
  idPaciente?: number;
}

export interface Produto {
  idProduto: number;
  dsNome: string;
  vlPreco1?: number;
  vlPreco2?: number;
  vlPreco3?: number;
  vlPreco4?: number;
}

export interface Agenda {
  idPaciente: number;
  dtConsulta: string;
  dtConsultaAte?: string;
  stPresenca?: boolean;
  idProduto: number;
  dsObs?: string;
  vlPreco?: number;
  stPagamento: number;
  nomePaciente?: string;
  nomeProduto?: string;
}

export interface Usuario {
  userid: string;
  password: string;
  dsNome?: string;
  idLastView: number;
  stRefreshAuto: boolean;
}

export interface GrupoUsuario {
  userid: string;
  groupid: string;
}

export interface Senha {
  idPaciente: number;
  dsSenha: string;
}

// DTOs para formulários
export interface PacienteFormData {
  dsNome: string;
  dsEmail?: string;
  dsProfissao?: string;
  dsEscolaridade?: string;
  stEstadocivil?: 'S' | 'C' | 'E' | 'V' | 'D';
  dtNascimento?: string;
  dsFilhos?: string;
  dsEndereco: string;
  dsBairro: string;
  nrCep: string;
  dsQueixas?: string;
  dsProbsaude?: string;
  dsAcompmedico?: string;
  dsRemedios?: string;
  stBebe?: 'S' | 'N';
  stFuma?: 'S' | 'N';
  stDrogas?: 'S' | 'N';
  stInsonia?: 'S' | 'N';
  dsCalmante?: string;
  stTratpsic?: 'S' | 'N';
  dsResultado?: string;
  dsObservacao?: string;
  dsFicha?: string;
  stCatValor?: '1' | '2' | '3' | '4';
  telefones: TelefoneFormData[];
  dsSenha?: string;
}

export interface TelefoneFormData {
  nrFone: string;
  tpFone: 'C' | 'R' | 'W';
}

export interface ProdutoFormData {
  dsNome: string;
  vlPreco1?: number;
  vlPreco2?: number;
  vlPreco3?: number;
  vlPreco4?: number;
}

export interface AgendaFormData {
  idPaciente: number;
  dtConsulta: string;
  dtConsultaAte?: string;
  stPresenca?: boolean;
  idProduto: number;
  dsObs?: string;
  vlPreco?: number;
  stPagamento: number;
}

// Enums para opções de seleção
export const ESTADO_CIVIL_OPTIONS = [
  { value: 'S', label: 'Solteiro' },
  { value: 'C', label: 'Casado' },
  { value: 'E', label: 'Separado' },
  { value: 'V', label: 'Viúvo' },
  { value: 'D', label: 'Divorciado' },
];

export const TIPO_TELEFONE_OPTIONS = [
  { value: 'C', label: 'Celular' },
  { value: 'R', label: 'Residencial' },
  { value: 'W', label: 'Comercial' },
];

export const CATEGORIA_VALOR_OPTIONS = [
  { value: '1', label: 'Categoria 1' },
  { value: '2', label: 'Categoria 2' },
  { value: '3', label: 'Categoria 3' },
  { value: '4', label: 'Categoria 4' },
];

export const STATUS_PAGAMENTO_OPTIONS = [
  { value: 0, label: 'A Receber' },
  { value: 1, label: 'Pago' },
  { value: 2, label: 'Abonado' },
  { value: 3, label: 'Calote' },
];

export const SIM_NAO_OPTIONS = [
  { value: 'S', label: 'Sim' },
  { value: 'N', label: 'Não' },
];

// Tipos para API responses
export interface ApiResponse<T> {
  data: T;
  message?: string;
  error?: string;
}

export interface PaginatedResponse<T> {
  data: T[];
  total: number;
  page: number;
  limit: number;
}

// Tipos para filtros e busca
export interface PacienteFilters {
  nome?: string;
  email?: string;
  categoriaValor?: string;
}

export interface AgendaFilters {
  idPaciente?: number;
  dataInicio?: string;
  dataFim?: string;
  stPresenca?: boolean;
  stPagamento?: number;
  idProduto?: number;
}

export interface RelatorioFilters {
  dataInicio: string;
  dataFim: string;
  idPaciente?: number;
  idProduto?: number;
  stPresenca?: boolean;
  stPagamento?: number;
}
