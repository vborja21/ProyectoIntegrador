import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Prestamista } from 'src/app/models/Prestamista/Prestamista';
import { PrestamistaDTO } from 'src/app/models/Prestamista/PrestamistaDTO';
import { PrestamistaUpdateDTO } from 'src/app/models/Prestamista/PrestamistaUpdateDTO';
import { Zona } from 'src/app/models/Zona';
import { PrestamistaService } from 'src/app/services/prestamista.service';
import { UtilsService } from 'src/app/services/utils.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-prestamista',
  templateUrl: './prestamista.component.html',
  styleUrls: ['./prestamista.component.css']
})
export class PrestamistaComponent implements OnInit {

  // Tabla
  prestamistas: Prestamista[] = [];

  // Formulario - Editar
  prestamistaEdit?: Prestamista

  // Form para hacer el registro
  registerForm!: FormGroup;

  // Form para actualizar
  updateForm!: FormGroup;


  // Dto Update
  prestamistaUpdateDTO?: PrestamistaUpdateDTO = {
    nombre: '',
    apellido: '',
    fechaNacimiento: '',
    correo: '',
    documento: 0,
    celular: 0
  }


  // Cargar Zonas
  zonas: Zona[] = [];

  constructor(private prestamistaService: PrestamistaService, private utilService: UtilsService, private fb: FormBuilder) {
    this.registerForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(3)]],
      apellido: ['', [Validators.required, Validators.minLength(3)]],
      fechaNacimiento: ['', [Validators.required, this.validateAge]],
      documento: ['', [Validators.required, Validators.pattern(/^\d{8}$/)]],
      celular: ['', [Validators.required, Validators.pattern(/^\d{9}$/)]],
      correo: ['', [Validators.required, Validators.email]],
      login: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.minLength(3)]],
      idZona: ['', [Validators.required]],
      rolDescripcion: ['Prestamista'],
      idJefePrestamista: ['']
    })

    this.updateForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(3)]],
      apellido: ['', [Validators.required, Validators.minLength(3)]],
      fechaNacimiento: ['', [Validators.required, this.validateAge]],
      correo: ['', [Validators.required, Validators.email]],
      documento: ['', [Validators.required, Validators.pattern(/^\d{8}$/)]],
      celular: ['', [Validators.required, Validators.pattern(/^\d{9}$/)]],
    })

    // Establecer el valor de idJefePrestamista desde localStorage
    const idJefePrestamista = localStorage.getItem('idEntidad');
    if (idJefePrestamista) {
      this.registerForm.patchValue({
        idJefePrestamista: +idJefePrestamista
      });
    }

    this.utilService.getZonasPorJefe(Number(idJefePrestamista)).subscribe(data => {
      this.zonas = data;
      console.log('Zonas:', data)
    });
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
    this.cargarPrestamistas();
  }
  
  cargarPrestamistas() {
    const idJefePrestamista = localStorage.getItem('idEntidad');
    if (idJefePrestamista) {
      this.prestamistaService.getPrestamistasByJefe(+idJefePrestamista).subscribe(data => {
        this.prestamistas = data;
      }, error => {
        console.error('Error al cargar los prestamistas:', error);
      });
    } else {
      console.log('No se encontró el ID del jefe prestamista en localStorage');
      // Manejar la situación donde no hay un ID de jefe prestamista disponible
    }
  }

  cargarPrestamista(id: number) {
    this.prestamistaService.getPrestamista(id).subscribe(data => {
      this.prestamistaEdit = data;
      this.cargarDatosAlDto();
    })
  }

  cargarDatosAlDto() {
    this.updateForm.patchValue ({
      nombre: this.prestamistaEdit?.nombre,
      apellido: this.prestamistaEdit?.apellido,
      fechaNacimiento: this.prestamistaEdit?.fechaNacimiento,
      correo: this.prestamistaEdit?.correo,
      documento: this.prestamistaEdit?.documento,
      celular: Number(this.prestamistaEdit?.celular)
    })
  }

  registrarPrestamista() {
    Swal.fire({
      title: '¿Estás seguro?',
      text: "¿Deseas registrar al nuevo Prestamista?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, registrar!',
      cancelButtonText: 'No, cancelar'
    }).then((result) => {
      if (result.isConfirmed && this.registerForm.valid) {
        this.prestamistaService.addPrestamista(this.registerForm.value).subscribe(
          data => {
            Swal.fire(
              'Registrado!',
              'El Prestamista ha sido registrado exitosamente.',
              'success'
            );
            this.cargarPrestamistas(); // Recargar los datos
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


  actualizarPrestamista() {
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
        this.prestamistaService.updatePrestamista(this.prestamistaEdit?.idPrestamista || 0, this.updateForm!.value)
          .subscribe(
            data => {
              Swal.fire(
                'Actualizado!',
                'El Jefe Prestamista ha sido actualizado exitosamente.',
                'success'
              );
              // Cerrar modal y resetear formulario aquí si es necesario
              // Recargar los datos
              this.cargarPrestamistas();
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
