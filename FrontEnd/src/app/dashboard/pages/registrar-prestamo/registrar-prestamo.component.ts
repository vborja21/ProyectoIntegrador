import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { Monto } from 'src/app/models/Monto';
import { PrestamoService } from 'src/app/services/prestamo.service';
import { UtilsService } from 'src/app/services/utils.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-registrar-prestamo',
  templateUrl: './registrar-prestamo.component.html',
  styleUrls: ['./registrar-prestamo.component.css']
})
export class RegistrarPrestamoComponent {

  // Id del Prestatario
  idPrestatario!: number
  // Montos - Grilla
  montos!: Monto[]
  // Días - Duracion
  diasDuracion!: number;
  // Fecha Fin
  fechaFin!: string;
  // Form para hacer el registro de un prestamo
  prestamoForm!: FormGroup;
  // otras propiedades...
  pagoDiario: number = 0;



  monto!: number;

  constructor(private utilService: UtilsService, private fb: FormBuilder, private prestamoService: PrestamoService) {
    this.utilService.getMontos().subscribe(data => {
      this.montos = data;
      console.log(this.montos)
    })

    // Establecer el valor de idPrestatario desde localStorage
    this.idPrestatario = Number(localStorage.getItem('idEntidad'))

    this.prestamoForm = this.fb.group({
      idPrestatario: [this.idPrestatario],
      idMonto: [''],
      fechaInicio: ['', [Validators.required, this.validarFechaInicio]],
      estadoPrestamo: [1],
    })

    this.prestamoForm.get('fechaInicio')?.valueChanges.subscribe(() => {
      if (this.diasDuracion) {
        this.calcularFechaFin();
      }
    });
  }

  validarFechaInicio(control: AbstractControl): ValidationErrors | null {
    const fechaEntrada = new Date(control.value);
    const hoy = new Date();
    hoy.setDate(hoy.getDate());
    hoy.setHours(0, 0, 0, 0);

    if (fechaEntrada >= hoy) {
      return null;
    }
    return { fechaInicioInvalida: 'La fecha de inicio debe ser a partir del día de mañana.' };
  }

  calcularFechaFin(): void {
    const fechaInicio: string = this.prestamoForm.get('fechaInicio')?.value;
    const diasDuracion: number = this.diasDuracion // Asegúrate de que el valor sea numérico

    if (fechaInicio && diasDuracion) {
      const fechaInicioDate = new Date(fechaInicio);
      fechaInicioDate.setDate(fechaInicioDate.getDate() + diasDuracion);
      this.fechaFin = fechaInicioDate.toISOString().split('T')[0]; // Formatea la fecha como YYYY-MM-DD
      console.log('Fecha Fin Calculada:', this.fechaFin);
      this.calcularPagoDiario();
    }
  }

  calcularPagoDiario(): void {
    const fechaInicio: Date = new Date(this.prestamoForm.get('fechaInicio')?.value);
    const fechaFin: Date = new Date(this.fechaFin);
    const diasLaborales = this.contarDiasLaborales(fechaInicio, fechaFin);
    if (diasLaborales > 0 && this.monto) {
      this.pagoDiario = Math.round((this.monto / diasLaborales) * 100) / 100; // Redondear a dos decimales
    } else {
      this.pagoDiario = 0;
    }
    console.log('Pago Diario Calculado:', this.pagoDiario);
  }


  private contarDiasLaborales(startDate: Date, endDate: Date): number {
    let count = 0;
    let currentDate = new Date(startDate);
    while (currentDate <= endDate) {
      const weekDay = currentDate.getDay();
      if (weekDay !== 0 && weekDay !== 6) { // 0 = Domingo, 6 = Sábado
        count++;
      }
      currentDate.setDate(currentDate.getDate() + 1);
    }
    return count;
  }


  selectValue(value: any): void {
    this.monto = value.monto;
    this.prestamoForm.get('idMonto')?.setValue(value.idMonto);
    this.diasDuracion = value.duracion;
    if (this.prestamoForm.get('fechaInicio')?.value) {
      this.calcularFechaFin();
    }
    console.log(this.prestamoForm.value)
    console.log("Días Duración: " + this.diasDuracion)
  }

  registrarPrestamo(): void {
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
      if (result.isConfirmed && this.prestamoForm.valid) {
        this.prestamoService.crearPrestamo(this.prestamoForm.value).subscribe(
          data => {
            Swal.fire(
              'Registrado!',
              'El Prestamo ha sido registrado exitosamente.',
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
            console.error('Error registrando el Jefe Prestamista:', error);
          }

          
        );
      }
    });
  }
}
