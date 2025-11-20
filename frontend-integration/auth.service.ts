import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from './environment';
import { Observable } from 'rxjs';

export interface ClientDto {
  id?: number;
  name?: string;
  cpf?: string;
  email?: string;
  phone?: string;
  username?: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private api = `${environment.apiBaseUrl}/api`;

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<ClientDto> {
    return this.http.post<ClientDto>(`${this.api}/auth/login`, { username, password });
  }

  registerCliente(name: string, cpf: string, email: string, phone: string, username: string, password: string) {
    const body = { name, cpf, email, phone, username, password };
    return this.http.post<ClientDto>(`${this.api}/auth/register`, body);
  }

  changePassword(dto: { cpf: string, currentPassword: string, newPassword: string }) {
    return this.http.post<void>(`${this.api}/auth/change-password`, dto);
  }

  logout() {
    localStorage.removeItem('currentUser');
    localStorage.removeItem('loggedIn');
  }
}
