import { AfterViewInit, Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PrestatarioDTO } from 'src/app/models/Prestatario/PrestatarioDTO';
import { PrestatarioService } from 'src/app/services/prestatario.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent {

  // Formulario - Registrar
  registerForm!: FormGroup;

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
      rolDescripcion: ['Prestatario']
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
  
  registrarPrestatario() {
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
        this.prestatarioService.addPrestatario(this.registerForm.value).subscribe(
          data => {
            Swal.fire(
              'Registrado!',
              'El Prestatario ha sido registrado exitosamente.',
              'success'
            );
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

}
