import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIndexETF } from 'app/shared/model/index-etf.model';

@Component({
  selector: 'jhi-index-etf-detail',
  templateUrl: './index-etf-detail.component.html'
})
export class IndexETFDetailComponent implements OnInit {
  indexETF: IIndexETF | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ indexETF }) => (this.indexETF = indexETF));
  }

  previousState(): void {
    window.history.back();
  }
}
