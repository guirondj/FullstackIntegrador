import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Beneficio } from '../models/beneficio';

@Injectable({
  providedIn: 'root'
})
export class BeneficioService {
  private http = inject(HttpClient);
  private api = 'http://localhost:8080/backend-module/api/v1/beneficios';

  listar(): Observable<Beneficio[]> {
    return this.http.get<Beneficio[]>(this.api);
  }

  transferir(payload: { fromId: number; toId: number; amount: number }): Observable<string> {
    return this.http.post(`${this.api}/transfer`, payload, { responseType: 'text' });
  }
}