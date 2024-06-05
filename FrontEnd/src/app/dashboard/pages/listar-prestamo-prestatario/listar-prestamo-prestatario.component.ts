import { Component } from '@angular/core';
import { Prestamo } from 'src/app/models/Prestamo';
import { PrestamoService } from 'src/app/services/prestamo.service';

@Component({
  selector: 'app-listar-prestamo-prestatario',
  templateUrl: './listar-prestamo-prestatario.component.html',
  styleUrls: ['./listar-prestamo-prestatario.component.css']
})
export class ListarPrestamoPrestatarioComponent {

  // Id del Prestatario
  idPrestatario!: number

  // Tabla
  prestamos: Prestamo[] = []

  // Establecer el valor de idJefePrestamista desde localStorage
  constructor(private prestamoService: PrestamoService) {
    this.idPrestatario = Number(localStorage.getItem('idEntidad'))
    this.prestamoService.getPrestamosPorPrestatario(this.idPrestatario).subscribe(data => {
      this.prestamos = data
      console.log(data)
    })
  }

}
