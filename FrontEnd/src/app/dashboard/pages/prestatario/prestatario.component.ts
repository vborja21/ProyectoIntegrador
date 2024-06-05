import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Prestatario } from 'src/app/models/Prestatario/Prestatario';
import { PrestatarioDTO } from 'src/app/models/Prestatario/PrestatarioDTO';
import { PrestatarioUpdateDTO } from 'src/app/models/Prestatario/PrestatarioUpdateDTO';
import { PrestatarioService } from 'src/app/services/prestatario.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-prestatario',
  templateUrl: './prestatario.component.html',
  styleUrls: ['./prestatario.component.css']
})
export class PrestatarioComponent implements OnInit {

  // Tabla
  prestatarios: Prestatario[] = [];

  // Formulario - Editar(GET)
  prestatarioEdit?: Prestatario

  // Formulario - Editar(POST)
  updateForm!: FormGroup;

  // Formulario - Registrar
  registerForm!: FormGroup;

  // DTO a mandar a la BD
  prestatarioUpdateDTO?: PrestatarioUpdateDTO = {
    nombre: '',
    apellido: '',
    fechaNacimiento: '',
    correo: '',
    documento: 0,
    celular: 0
  }

  constructor(private prestatarioService: PrestatarioService, private fb: FormBuilder) { 
    this.registerForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(3)]],
      apellido: ['', [Validators.required, Validators.minLength(3)]],
      fechaNacimiento: ['', [Validators.required, this.validateAge]],
      documento: ['', [Validators.required, Validators.pattern(/^\d{8}$/)]],
      celular: ['', [Validators.required, Validators.pattern(/^\d{9}$/)]],
      correo: ['', [Validators.required, Validators.email]],
      login: ['', [Validators.required, Validators.minLength(5)]],
      password: ['', [Validators.required, Validators.minLength(3)]],
      rolDescripcion: ['Prestatario'],
      idPrestamista: ['']
    });

    this.updateForm = this.fb.group({
      nombre: ["", [Validators.required, Validators.minLength(3)]],
      apellido: ["", [Validators.required, Validators.minLength(3)]],
      fechaNacimiento: ["", [Validators.required, this.validateAge]],
      correo: ["", [Validators.required, Validators.email]],
      documento: ["", [Validators.required, Validators.pattern(/^\d{8}$/)]],
      celular: ["", [Validators.required, Validators.pattern(/^\d{9}$/)]]
    })

    // Establecer el valor de idPrestamista en el formulario de registro
    const idPrestamista = localStorage.getItem('idEntidad');
    if(idPrestamista){
      this.registerForm.patchValue({
        idPrestamista: +idPrestamista
      })
    }

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
    this.cargarPrestatarios();
  }

  cargarPrestatarios(){
    const idPrestamista = localStorage.getItem('idEntidad');
    if(idPrestamista){
      this.prestatarioService.getPrestatariosByPrestamista(+idPrestamista).subscribe(data => {
        this.prestatarios = data;
      }, error => {
        console.error('Error cargando los Prestatarios:', error);
      })
    } else {
      console.log("No se encontró el ID del Prestatario en localStorage");
    }
  }

  cargarPrestatario(id: number){
    this.prestatarioService.getPrestatario(id).subscribe(data => {
      this.prestatarioEdit = data;
      this.cargarDatosAlDto()
    })
  }

  cargarDatosAlDto(){
    this.updateForm.patchValue({
      nombre: this.prestatarioEdit?.nombre,
      apellido: this.prestatarioEdit?.apellido,
      fechaNacimiento: this.prestatarioEdit?.fechaNacimiento,
      correo: this.prestatarioEdit?.correo,
      documento: this.prestatarioEdit?.documento,
      celular: this.prestatarioEdit?.celular
    })
  }

  registrarPrestatario() {
    Swal.fire({
      title: '¿Estás seguro?',
      text: "¿Deseas registrar al nuevo Prestatario?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, registrar!',
      cancelButtonText: 'No, cancelar'
    }).then((result) => {
      if (result.isConfirmed && this.registerForm.valid) {
        this.prestatarioService.addPrestatario(this.registerForm.value).subscribe(
          data => {
            Swal.fire(
              'Registrado!',
              'El Prestatario ha sido registrado exitosamente.',
              'success'
            );
            this.cargarPrestatarios(); // Recargar los datos
          },
          error => {
            const errorMessage = error.error.message || 'Ocurrió un error desconocido.';
            Swal.fire(
              'Error!',
              errorMessage,
              'error'
            );
            console.error('Error registrando el Prestatario:', error);
          }
        );
      }
    });
  }

  actualizarPrestatario() {
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
        this.prestatarioService.updatePrestatario(this.prestatarioEdit?.idPrestatario || 0, this.updateForm!.value)
          .subscribe(
            data => {
              Swal.fire(
                'Actualizado!',
                'El Prestatario ha sido actualizado exitosamente.',
                'success'
              );
              // Cerrar modal y resetear formulario aquí si es necesario
              // Recargar los datos
              this.cargarPrestatarios();
            },
            error => {
              Swal.fire(
                'Error!',
                'Hubo un problema al actualizar el Prestatario.',
                'error'
              );
              console.error('Error actualizando el Prestatario:', error);
            }
          );
      }
    });
  }


}
