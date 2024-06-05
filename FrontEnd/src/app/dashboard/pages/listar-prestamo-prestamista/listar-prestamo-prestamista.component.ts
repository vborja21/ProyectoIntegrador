import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Prestamo } from 'src/app/models/Prestamo';
import { PrestamoService } from 'src/app/services/prestamo.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-listar-prestamo-prestamista',
  templateUrl: './listar-prestamo-prestamista.component.html',
  styleUrls: ['./listar-prestamo-prestamista.component.css']
})
export class ListarPrestamoPrestamistaComponent {

  prestamos: Prestamo[] = [];

  // Ver Detalle Prestamo
  prestamo!: Prestamo

  idPrestamista!: number;  // Asumimos que tienes un ID de prestamista por defecto o configurado

  currentFilter: any = {};  // Objeto para almacenar los valores actuales del filtro


  constructor(private prestamoService: PrestamoService) {
    const idPrestamista = localStorage.getItem('idEntidad');
    if (idPrestamista) {
      this.idPrestamista = parseInt(idPrestamista);
    }
  }

  onFilter(form: NgForm) {
    this.currentFilter = form.value;  // Almacena los valores del formulario de filtro
    this.applyFilter();
  }

  applyFilter() {
    const { prestatario, fechaDesde, fechaFin } = this.currentFilter;

    this.prestamoService.getPrestamosPorPrestamista(this.idPrestamista, prestatario, fechaDesde, fechaFin)
      .subscribe({
        next: (prestamos) => {
          this.prestamos = prestamos;
        },
        error: (error) => {
          console.error('Error al obtener prestamos:', error);
        }
      });
  }

  cargarPrestamo(id: number): void {
    this.prestamoService.getPrestamorPorId(id).subscribe(data => {
      this.prestamo = data

      console.log(this.prestamo)
    });

  }

  aprobarPrestamo(id: number | undefined): void {

    if (id === undefined) {
      Swal.fire(
        'Error',
        'No se pudo identificar el ID del préstamo.',
        'error'
      );
      return;
    }

    Swal.fire({
      title: '¿Estás seguro?',
      text: "¿Deseas aprobar el prestamo?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, Aprobar!',
      cancelButtonText: 'No, cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.prestamoService.actualizarEstadoPrestamo(id, 2).subscribe(
          data => {
            Swal.fire(
              'Aprobado!',
              'El Prestamo ha sido aprobado exitosamente.',
              'success'
            );
            this.prestamo = data;
            this.applyFilter();
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

  rechazarPrestamo(id: number | undefined): void {
    if (id === undefined) {
      Swal.fire(
        'Error',
        'No se pudo identificar el ID del préstamo.',
        'error'
      );
      return;
    }

    Swal.fire({
      title: '¿Estás seguro?',
      text: "¿Deseas rechazar el prestamo?",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, Rechazar!',
      cancelButtonText: 'No, cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.prestamoService.actualizarEstadoPrestamo(id, 3).subscribe(
          data => {
            Swal.fire(
              'Rechazado!',
              'El Prestamo ha sido rechazado exitosamente.',
              'success'
            );
            this.prestamo = data;
            this.applyFilter();
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
