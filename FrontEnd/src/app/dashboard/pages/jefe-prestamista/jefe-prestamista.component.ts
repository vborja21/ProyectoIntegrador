import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { JefePrestamista } from 'src/app/models/JefePrestamista/JefePrestamista';
import { JefePrestamistaDTO } from 'src/app/models/JefePrestamista/JefePrestamistaDTO';
import { JefePrestamistaUpdateDTO } from 'src/app/models/JefePrestamista/JefePrestamistaUpdateDTO';
import { Ubicacion } from 'src/app/models/Ubicacion';
import { JefePrestamistaService } from 'src/app/services/jefe-prestamista.service';
import { UtilsService } from 'src/app/services/utils.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-jefe-prestamista',
  templateUrl: './jefe-prestamista.component.html',
  styleUrls: ['./jefe-prestamista.component.css']
})
export class JefePrestamistaComponent {



  // Tabla
  jefesPrestamistas: JefePrestamista[] = [];

  // Formulario - Editar
  jefePrestamistaEdit?: JefePrestamista

  // Form para hacer las validaciones
  updateForm!: FormGroup;

  // Form para hacer el registro
  registerForm!: FormGroup;

  // DTO a mandar a la BD
  jefePrestamistaUpdateDTO?: JefePrestamistaUpdateDTO = {
    nombre: '',
    apellido: '',
    fechaNacimiento: '',
    correo: '',
    documento: 0,
    celular: 0
  }

  // Cargar Ubicacion
  ubicaciones: Ubicacion[] = [];

  // Formulario - Crear
  

  constructor(private jefePrestamistaService: JefePrestamistaService, private utilService: UtilsService, private fb: FormBuilder) {
    this.utilService.getUbicaciones().subscribe(data => {
      this.ubicaciones = data;
    });

    this.registerForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(3)]],
      apellido: ['', [Validators.required, Validators.minLength(3)]],
      fechaNacimiento: ['', [Validators.required, this.validateAge]],
      documento: ['', [Validators.required, Validators.pattern(/^\d{8}$/)]],
      celular: ['', [Validators.required, Validators.pattern(/^\d{9}$/)]],
      correo: ['', [Validators.required, Validators.email]],
      login: ['', [Validators.required, Validators.minLength(5)]],
      password: ['', [Validators.required, Validators.minLength(3)]],
      idUbicacion: ['', [Validators.required]],
      rolDescripcion: ['JefePrestamista']
    });

    this.updateForm = this.fb.group({
      nombre: ["", [Validators.required, Validators.minLength(3)]],
      apellido: ["", [Validators.required, Validators.minLength(3)]],
      fechaNacimiento: ["", [Validators.required, this.validateAge]],
      correo: ["", [Validators.required, Validators.email]],
      documento: ["", [Validators.required, Validators.pattern(/^\d{8}$/)]],
      celular: ["", [Validators.required, Validators.pattern(/^\d{9}$/)]],
    })

    
  }

  validateAge(control: AbstractControl): { [key: string]: any } | null {
    const date = new Date(control.value);
    const today = new Date();
    let age = today.getFullYear() - date.getFullYear();
    const m = today.getMonth() - date.getMonth();
    if (m < 0 || (m === 0 && today.getDate() < date.getDate())) {
      age--;
    }
    return age >= 18 ? null : { 'ageInvalid': 'Debe ser mayor de 18 años' };
  }


  ngOnInit(): void {
    this.jefePrestamistaService.getJefesPrestamistas().subscribe(data => {
      this.jefesPrestamistas = data;
    });
  }

  cargarJefesPrestamistas() {
    this.jefePrestamistaService.getJefesPrestamistas().subscribe(data => {
      this.jefesPrestamistas = data;
    }, error => {
      console.error('Error cargando Jefes Prestamistas:', error);
    });
  }

  cargarJefePrestamista(id: number) {
    this.jefePrestamistaService.getJefePrestamista(id).subscribe(data => {
      this.jefePrestamistaEdit = data;
      this.cargarDatosAlDto();
    })
  }

  cargarDatosAlDto() {
    this.updateForm.patchValue({
      nombre: this.jefePrestamistaEdit?.nombre,
      apellido: this.jefePrestamistaEdit?.apellido,
      fechaNacimiento: this.jefePrestamistaEdit?.fechaNacimiento,
      correo: this.jefePrestamistaEdit?.correo,
      documento: this.jefePrestamistaEdit?.documento,
      celular: this.jefePrestamistaEdit?.celular
    });
  }

  registrarJefePrestamista() {
    Swal.fire({
      title: '¿Estás seguro?',
      text: "¿Deseas registrar al nuevo Jefe Prestamista?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, registrar!',
      cancelButtonText: 'No, cancelar'
    }).then((result) => {
      if (result.isConfirmed && this.registerForm.valid) {
        this.jefePrestamistaService.createJefePrestamista(this.registerForm.value).subscribe(
          data => {
            Swal.fire(
              'Registrado!',
              'El Jefe Prestamista ha sido registrado exitosamente.',
              'success'
            );
            this.cargarJefesPrestamistas(); // Recargar los datos
            // Cerrar modal y resetear formulario aquí si es necesario
          },
          error => {
            const errorMessage = error.error.message || 'Ocurrió un error desconocido.';
            Swal.fire(
              'Error!',
              errorMessage,
              'error'
            );
            console.error('Error registrando el Jefe Prestamista:', error);
          }
        );
      }
    });
  }
  

  actualizarJefePrestamista() {
    Swal.fire({
      title: '¿Estás seguro?',
      text: "¿Deseas actualizar los datos del Jefe Prestamista?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, actualizar!',
      cancelButtonText: 'No, cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.jefePrestamistaService.updateJefePrestamista(this.jefePrestamistaEdit?.idJefePrestamista || 0, this.updateForm!.value)
          .subscribe(
            data => {
              Swal.fire(
                'Actualizado!',
                'El Jefe Prestamista ha sido actualizado exitosamente.',
                'success'
              );
              // Cerrar modal y resetear formulario aquí si es necesario
              // Recargar los datos
              this.cargarJefesPrestamistas();
            },
            error => {
              Swal.fire(
                'Error!',
                'Hubo un problema al actualizar el Jefe Prestamista.',
                'error'
              );
              console.error('Error actualizando el Jefe Prestamista:', error);
            }
          );
      }
    });
  }
  



  


}
