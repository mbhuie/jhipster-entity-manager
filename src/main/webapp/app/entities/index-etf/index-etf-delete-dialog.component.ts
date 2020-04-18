import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIndexETF } from 'app/shared/model/index-etf.model';
import { IndexETFService } from './index-etf.service';

@Component({
  templateUrl: './index-etf-delete-dialog.component.html'
})
export class IndexETFDeleteDialogComponent {
  indexETF?: IIndexETF;

  constructor(protected indexETFService: IndexETFService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.indexETFService.delete(id).subscribe(() => {
      this.eventManager.broadcast('indexETFListModification');
      this.activeModal.close();
    });
  }
}
