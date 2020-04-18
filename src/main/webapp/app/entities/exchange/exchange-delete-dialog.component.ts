import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExchange } from 'app/shared/model/exchange.model';
import { ExchangeService } from './exchange.service';

@Component({
  templateUrl: './exchange-delete-dialog.component.html'
})
export class ExchangeDeleteDialogComponent {
  exchange?: IExchange;

  constructor(protected exchangeService: ExchangeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.exchangeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('exchangeListModification');
      this.activeModal.close();
    });
  }
}
