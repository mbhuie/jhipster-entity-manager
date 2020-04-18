import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIndex } from 'app/shared/model/index.model';
import { IndexService } from './index.service';
import { IndexDeleteDialogComponent } from './index-delete-dialog.component';

@Component({
  selector: 'jhi-index',
  templateUrl: './index.component.html'
})
export class IndexComponent implements OnInit, OnDestroy {
  indices?: IIndex[];
  eventSubscriber?: Subscription;

  constructor(protected indexService: IndexService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.indexService.query().subscribe((res: HttpResponse<IIndex[]>) => (this.indices = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInIndices();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIndex): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInIndices(): void {
    this.eventSubscriber = this.eventManager.subscribe('indexListModification', () => this.loadAll());
  }

  delete(index: IIndex): void {
    const modalRef = this.modalService.open(IndexDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.index = index;
  }
}
