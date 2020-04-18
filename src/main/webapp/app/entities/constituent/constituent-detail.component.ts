import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConstituent } from 'app/shared/model/constituent.model';

@Component({
  selector: 'jhi-constituent-detail',
  templateUrl: './constituent-detail.component.html'
})
export class ConstituentDetailComponent implements OnInit {
  constituent: IConstituent | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ constituent }) => (this.constituent = constituent));
  }

  previousState(): void {
    window.history.back();
  }
}
