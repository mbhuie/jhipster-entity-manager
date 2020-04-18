import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IExchange } from 'app/shared/model/exchange.model';
import { ExchangeService } from './exchange.service';
import { ExchangeDeleteDialogComponent } from './exchange-delete-dialog.component';

@Component({
  selector: 'jhi-exchange',
  templateUrl: './exchange.component.html'
})
export class ExchangeComponent implements OnInit, OnDestroy {
  exchanges?: IExchange[];
  eventSubscriber?: Subscription;

  constructor(protected exchangeService: ExchangeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.exchangeService.query().subscribe((res: HttpResponse<IExchange[]>) => (this.exchanges = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInExchanges();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IExchange): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInExchanges(): void {
    this.eventSubscriber = this.eventManager.subscribe('exchangeListModification', () => this.loadAll());
  }

  delete(exchange: IExchange): void {
    const modalRef = this.modalService.open(ExchangeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.exchange = exchange;
  }
}
