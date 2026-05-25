export interface Opcion {
  id: number;
  nombre: string;
  padreOpcionId: number | null;
  ruta: string | null;
  icono: string | null;
  orden: number;
  hijos: Opcion[];
}
