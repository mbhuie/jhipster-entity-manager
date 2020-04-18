import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IExchange, Exchange } from 'app/shared/model/exchange.model';
import { ExchangeService } from './exchange.service';
import { IStock } from 'app/shared/model/stock.model';
import { StockService } from 'app/entities/stock/stock.service';

@Component({
  selector: 'jhi-exchange-update',
  templateUrl: './exchange-update.component.html'
})
export class ExchangeUpdateComponent implements OnInit {
  isSaving = false;
  mics: IStock[] = [];

  editForm = this.fb.group({
    id: [],
    mic: [],
    name: [],
    mic: []
  });

  constructor(
    protected exchangeService: ExchangeService,
    protected stockService: StockService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ exchange }) => {
      this.updateForm(exchange);

      this.stockService
        .query({ filter: 'exchange-is-null' })
        .pipe(
          map((res: HttpResponse<IStock[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IStock[]) => {
          if (!exchange.mic || !exchange.mic.id) {
            this.mics = resBody;
          } else {
            this.stockService
              .find(exchange.mic.id)
              .pipe(
                map((subRes: HttpResponse<IStock>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IStock[]) => (this.mics = concatRes));
          }
        });
    });
  }

  updateForm(exchange: IExchange): void {
    this.editForm.patchValue({
      id: exchange.id,
      mic: exchange.mic,
      name: exchange.name,
      mic: exchange.mic
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const exchange = this.createFromForm();
    if (exchange.id !== undefined) {
      this.subscribeToSaveResponse(this.exchangeService.update(exchange));
    } else {
      this.subscribeToSaveResponse(this.exchangeService.create(exchange));
    }
  }

  private createFromForm(): IExchange {
    return {
      ...new Exchange(),
      id: this.editForm.get(['id'])!.value,
      mic: this.editForm.get(['mic'])!.value,
      name: this.editForm.get(['name'])!.value,
      mic: this.editForm.get(['mic'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExchange>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IStock): any {
    return item.id;
  }
}
