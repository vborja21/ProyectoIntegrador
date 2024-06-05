import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Prestatario } from '../models/Prestatario/Prestatario';
import { PrestatarioDTO } from '../models/Prestatario/PrestatarioDTO';
import { PrestatarioUpdateDTO } from '../models/Prestatario/PrestatarioUpdateDTO';

@Injectable({
  providedIn: 'root'
})
export class PrestatarioService {

  private apiUrl = 'http://localhost:8081/api/prestatario';

  constructor(private http: HttpClient) {

  }

  getPrestatarios(): Observable<Prestatario[]> {
    return this.http.get<Prestatario[]>(`${this.apiUrl}/findAll`);
  }

  getPrestatariosByPrestamista(id: number): Observable<Prestatario[]> {
    return this.http.get<Prestatario[]>(`${this.apiUrl}/findAllByPrestamistaId/${id}`);
  }

  getPrestatario(id: number): Observable<Prestatario> {
    return this.http.get<Prestatario>(`${this.apiUrl}/findById/${id}`);
  }

  addPrestatario(prestatarioDTO: PrestatarioDTO): Observable<Prestatario> {
    return this.http.post<Prestatario>(`${this.apiUrl}/prestatario`, prestatarioDTO);
  }

  updatePrestatario(id: number, prestatarioUpdateDTO: PrestatarioUpdateDTO): Observable<any> {
    return this.http.put(`${this.apiUrl}/prestatario/${id}`, prestatarioUpdateDTO);
  }

}
