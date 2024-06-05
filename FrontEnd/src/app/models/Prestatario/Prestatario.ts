import { Prestamista } from "../Prestamista/Prestamista";
import { Usuario } from "../Usuario";

export interface Prestatario {
    idPrestatario?: number;
    nombre?: string;
    apellido?: string;
    fechaNacimiento?: string; 
    fechaCreacion?: string; 
    correo?: string;
    documento?: number;
    celular?: number;
    usuario?: Usuario;
    prestamista?: Prestamista[];
}