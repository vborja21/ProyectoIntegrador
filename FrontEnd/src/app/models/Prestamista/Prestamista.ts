import { JefePrestamista } from "../JefePrestamista/JefePrestamista";
import { Prestatario } from "../Prestatario/Prestatario";
import { Usuario } from "../Usuario";
import { Zona } from "../Zona";

export interface Prestamista {
    idPrestamista?  : number;
    nombre?         : string;
    apellido?       : string;
    fechaNacimiento?: string;
    fechaCreacion?  : string;
    correo?         : string;
    documento?      : number;
    celular?        : number;
    usuario?        : Usuario;
    prestatario?    : Prestatario[];
    jefePrestamista?: JefePrestamista;
    idZona?         : Zona;
}