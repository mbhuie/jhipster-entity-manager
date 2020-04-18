import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConstituent, Constituent } from 'app/shared/model/constituent.model';
import { ConstituentService } from './constituent.service';
import { IIndex } from 'app/shared/model/index.model';
import { IndexService } from 'app/entities/index/index.service';

@Component({
  selector: 'jhi-constituent-update',
  templateUrl: './constituent-update.component.html'
})
export class ConstituentUpdateComponent implements OnInit {
  isSaving = false;
  indices: IIndex[] = [];

  editForm = this.fb.group({
    id: [],
    ric: [],
    isin: [],
    sedol: [],
    currency: [],
    issueType: [],
    mic: [],
    centerCode: [],
    sharesOutstanding: [],
    index: []
  });

  constructor(
    protected constituentService: ConstituentService,
    protected indexService: IndexService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ constituent }) => {
      this.updateForm(constituent);

      this.indexService.query().subscribe((res: HttpResponse<IIndex[]>) => (this.indices = res.body || []));
    });
  }

  updateForm(constituent: IConstituent): void {
    this.editForm.patchValue({
      id: constituent.id,
      ric: constituent.ric,
      isin: constituent.isin,
      sedol: constituent.sedol,
      currency: constituent.currency,
      issueType: constituent.issueType,
      mic: constituent.mic,
      centerCode: constituent.centerCode,
      sharesOutstanding: constituent.sharesOutstanding,
      index: constituent.index
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const constituent = this.createFromForm();
    if (constituent.id !== undefined) {
      this.subscribeToSaveResponse(this.constituentService.update(constituent));
    } else {
      this.subscribeToSaveResponse(this.constituentService.create(constituent));
    }
  }

  private createFromForm(): IConstituent {
    return {
      ...new Constituent(),
      id: this.editForm.get(['id'])!.value,
      ric: this.editForm.get(['ric'])!.value,
      isin: this.editForm.get(['isin'])!.value,
      sedol: this.editForm.get(['sedol'])!.value,
      currency: this.editForm.get(['currency'])!.value,
      issueType: this.editForm.get(['issueType'])!.value,
      mic: this.editForm.get(['mic'])!.value,
      centerCode: this.editForm.get(['centerCode'])!.value,
      sharesOutstanding: this.editForm.get(['sharesOutstanding'])!.value,
      index: this.editForm.get(['index'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConstituent>>): void {
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

  trackById(index: number, item: IIndex): any {
    return item.id;
  }
}
