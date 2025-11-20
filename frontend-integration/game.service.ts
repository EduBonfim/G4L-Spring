import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from './environment';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class GameService {
  private api = `${environment.apiBaseUrl}/api`;
  constructor(private http: HttpClient) {}
  getAllGames(): Observable<any[]> { return this.http.get<any[]>(`${this.api}/games`); }
  getGame(id: number) { return this.http.get<any>(`${this.api}/games/${id}`); }
  createGame(game: any) { return this.http.post<any>(`${this.api}/games`, game); }
  updateGame(id: number, game: any) { return this.http.put<any>(`${this.api}/games/${id}`, game); }
  deleteGame(id: number) { return this.http.delete<void>(`${this.api}/games/${id}`); }
}
