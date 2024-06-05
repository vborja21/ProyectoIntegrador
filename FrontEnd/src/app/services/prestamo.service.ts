import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Prestamo } from '../models/Prestamo';
import { PrestamoDTO } from '../models/PrestamoDTO';

@Injectable({
  providedIn: 'root'
})
export class PrestamoService {

  private apiUrl = 'http://localhost:8081/api/prestamos'

  constructor(private http: HttpClient) { }

  getPrestamosPorPrestatario(id: number): Observable<Prestamo[]> {
    return this.http.get<Prestamo[]>(`${this.apiUrl}/findAll/${id}`)
  }

  crearPrestamo(prestamoDto: PrestamoDTO) : Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/crearPrestamo`, prestamoDto)
  }

  getPrestamosPorPrestamista(idPrestamista: number, nombre?: string, fechaInicio?: string, fechaFin?: string): Observable<Prestamo[]> {
    let params = new HttpParams();
    if (nombre) {
      params = params.append('nombre', nombre);
    }
    if (fechaInicio) {
      params = params.append('fechaInicio', fechaInicio);
    }
    if (fechaFin) {
      params = params.append('fechaFin', fechaFin);
    }

    return this.http.get<Prestamo[]>(`${this.apiUrl}/findPrestamosPorFiltro/${idPrestamista}`, { params: params });
  }

  getPrestamorPorId(id: number): Observable<Prestamo> {
    return this.http.get<Prestamo>(`${this.apiUrl}/findByIdPrestamo/${id}`);
  }

  actualizarEstadoPrestamo(id: number, estado: number): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/prestamo/${id}/${estado}`, null);
  }
}
