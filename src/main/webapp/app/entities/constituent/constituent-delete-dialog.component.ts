import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConstituent } from 'app/shared/model/constituent.model';
import { ConstituentService } from './constituent.service';

@Component({
  templateUrl: './constituent-delete-dialog.component.html'
})
export class ConstituentDeleteDialogComponent {
  constituent?: IConstituent;

  constructor(
    protected constituentService: ConstituentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.constituentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('constituentListModification');
      this.activeModal.close();
    });
  }
}
