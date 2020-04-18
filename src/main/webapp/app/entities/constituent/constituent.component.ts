import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConstituent } from 'app/shared/model/constituent.model';
import { ConstituentService } from './constituent.service';
import { ConstituentDeleteDialogComponent } from './constituent-delete-dialog.component';

@Component({
  selector: 'jhi-constituent',
  templateUrl: './constituent.component.html'
})
export class ConstituentComponent implements OnInit, OnDestroy {
  constituents?: IConstituent[];
  eventSubscriber?: Subscription;

  constructor(
    protected constituentService: ConstituentService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.constituentService.query().subscribe((res: HttpResponse<IConstituent[]>) => (this.constituents = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConstituents();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConstituent): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInConstituents(): void {
    this.eventSubscriber = this.eventManager.subscribe('constituentListModification', () => this.loadAll());
  }

  delete(constituent: IConstituent): void {
    const modalRef = this.modalService.open(ConstituentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.constituent = constituent;
  }
}
