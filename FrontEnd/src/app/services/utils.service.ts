import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Ubicacion } from '../models/Ubicacion';
import { Zona } from '../models/Zona';
import { Monto } from '../models/Monto';

@Injectable({
  providedIn: 'root'
})
export class UtilsService {

  private apiUrl = 'http://localhost:8081/api/utils'

  constructor(private http: HttpClient) {

  }

  getUbicaciones(): Observable<Ubicacion[]> {
    return this.http.get<Ubicacion[]>(`${this.apiUrl}/ubicaciones`);
  }

  getZonas(): Observable<Zona[]> {
    return this.http.get<Zona[]>(`${this.apiUrl}/zonas`)
  }

  getZonasPorJefe(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/zonas/${id}`)
  }
  // http://localhost:8081/api/utils/zonas/8

  getMontos(): Observable<Monto[]> {
    return this.http.get<Monto[]>(`${this.apiUrl}/montos`)
  }

  getMontoPorId(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/montos/${id}`)
  }

}
