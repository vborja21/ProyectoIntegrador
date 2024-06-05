import { Monto } from "./Monto";
import { Prestatario } from "./Prestatario/Prestatario";

export interface Prestamo{
    idPrestamo?: number;
    prestatario?: Prestatario;
    fechaInicio?: string;
    fechaFin?: string;
    monto?: Monto;
    pagoDiario?: number;
    estadoPrestamo?: number;
}