import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIndexETF } from 'app/shared/model/index-etf.model';
import { IndexETFService } from './index-etf.service';
import { IndexETFDeleteDialogComponent } from './index-etf-delete-dialog.component';

@Component({
  selector: 'jhi-index-etf',
  templateUrl: './index-etf.component.html'
})
export class IndexETFComponent implements OnInit, OnDestroy {
  indexETFS?: IIndexETF[];
  eventSubscriber?: Subscription;

  constructor(protected indexETFService: IndexETFService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.indexETFService.query().subscribe((res: HttpResponse<IIndexETF[]>) => (this.indexETFS = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInIndexETFS();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIndexETF): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInIndexETFS(): void {
    this.eventSubscriber = this.eventManager.subscribe('indexETFListModification', () => this.loadAll());
  }

  delete(indexETF: IIndexETF): void {
    const modalRef = this.modalService.open(IndexETFDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.indexETF = indexETF;
  }
}
