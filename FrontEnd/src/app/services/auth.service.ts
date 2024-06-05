import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginRequest } from '../models/LoginRequest';
import { BehaviorSubject, Observable, map } from 'rxjs';
import { JwtResponse } from '../models/JwtResponse';

import  { jwtDecode }  from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private currentUserSubject: BehaviorSubject<JwtResponse | null>;
  public currentUser: Observable<JwtResponse | null>;

  private baseUrl = 'http://localhost:8081/auth';

  constructor(private http: HttpClient) {
    const storedUser = localStorage.getItem('currentUser');
    const currentUser = storedUser ? JSON.parse(storedUser) : null;
    this.currentUserSubject = new BehaviorSubject<JwtResponse | null>(currentUser);
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): JwtResponse | null {
    return this.currentUserSubject.value;
  }

  login(credentials: LoginRequest): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(`${this.baseUrl}/login`, credentials)
      .pipe(map(response => {
        localStorage.setItem('currentUser', JSON.stringify(response));
        this.currentUserSubject.next(response);

        // Decodificar el token para extraer idEntidad
      const decodedToken = jwtDecode<any>(response.token!);
      if (decodedToken.idEntidad) {
        localStorage.setItem('idEntidad', decodedToken.idEntidad.toString());
      }

        return response;
      }));
  }

  logout() {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);  
  }

  isAuthenticated(): boolean {
    return !!this.currentUserSubject.value;
  }
  
  public getUserRole(): string[] {
    const currentUser = this.currentUserValue;
    if(currentUser && currentUser.token){
      const decoded = jwtDecode<any>(currentUser.token);
      return decoded.auth;
    }

    return []
  }

}
