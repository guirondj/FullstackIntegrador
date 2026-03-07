import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Beneficio } from './models/beneficio';
import { BeneficioService } from './services/beneficio.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  private beneficioService = inject(BeneficioService);

  beneficios: Beneficio[] = [];

  fromId!: number;
  toId!: number;
  amount!: number;

  mensagem = '';
  erro = '';

  ngOnInit(): void {
    this.carregarBeneficios();
  }

  carregarBeneficios(): void {
    this.erro = '';
    this.beneficioService.listar().subscribe({
      next: (data) => {
        this.beneficios = data;
      },
      error: () => {
        this.erro = 'Erro ao carregar benefícios.';
      }
    });
  }

  transferir(): void {
    this.mensagem = '';
    this.erro = '';

    this.beneficioService.transferir({
      fromId: this.fromId,
      toId: this.toId,
      amount: this.amount
    }).subscribe({
      next: (res) => {
        this.mensagem = res || 'Transferência realizada com sucesso.';
        this.carregarBeneficios();
      },
      error: () => {
        this.erro = 'Erro ao realizar transferência.';
      }
    });
  }
}