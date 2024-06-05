import { Inversionista } from "../Inversionista";
import { Ubicacion } from "../Ubicacion";
import { Usuario } from "../Usuario";

export interface JefePrestamista {
    idJefePrestamista?      : number;
    nombre?                 : string;
    apellido?               : string;
    fechaNacimiento?        : string;
    fechaCreacion?          : string;
    documento?              : number;
    celular?                : string; 
    correo?                 : string;
    ubicacion?              : Ubicacion;
    usuario?                : Usuario;
    inversionista?          : Inversionista; 
    prestamistas?           : any[]; 
}