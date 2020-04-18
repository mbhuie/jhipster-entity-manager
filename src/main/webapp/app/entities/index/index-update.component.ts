import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIndex, Index } from 'app/shared/model/index.model';
import { IndexService } from './index.service';

@Component({
  selector: 'jhi-index-update',
  templateUrl: './index-update.component.html'
})
export class IndexUpdateComponent implements OnInit {
  isSaving = false;
  asAtDateDp: any;

  editForm = this.fb.group({
    id: [],
    basketCurrency: [],
    asAtDate: [],
    basketInstrument: [],
    basketInstrumentType: [],
    basketMinorUnits: [],
    basketName: [],
    basketRic: [],
    basketType: [],
    family: [],
    fundCurrency: [],
    isEtf: [],
    isEtc: []
  });

  constructor(protected indexService: IndexService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ index }) => {
      this.updateForm(index);
    });
  }

  updateForm(index: IIndex): void {
    this.editForm.patchValue({
      id: index.id,
      basketCurrency: index.basketCurrency,
      asAtDate: index.asAtDate,
      basketInstrument: index.basketInstrument,
      basketInstrumentType: index.basketInstrumentType,
      basketMinorUnits: index.basketMinorUnits,
      basketName: index.basketName,
      basketRic: index.basketRic,
      basketType: index.basketType,
      family: index.family,
      fundCurrency: index.fundCurrency,
      isEtf: index.isEtf,
      isEtc: index.isEtc
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const index = this.createFromForm();
    if (index.id !== undefined) {
      this.subscribeToSaveResponse(this.indexService.update(index));
    } else {
      this.subscribeToSaveResponse(this.indexService.create(index));
    }
  }

  private createFromForm(): IIndex {
    return {
      ...new Index(),
      id: this.editForm.get(['id'])!.value,
      basketCurrency: this.editForm.get(['basketCurrency'])!.value,
      asAtDate: this.editForm.get(['asAtDate'])!.value,
      basketInstrument: this.editForm.get(['basketInstrument'])!.value,
      basketInstrumentType: this.editForm.get(['basketInstrumentType'])!.value,
      basketMinorUnits: this.editForm.get(['basketMinorUnits'])!.value,
      basketName: this.editForm.get(['basketName'])!.value,
      basketRic: this.editForm.get(['basketRic'])!.value,
      basketType: this.editForm.get(['basketType'])!.value,
      family: this.editForm.get(['family'])!.value,
      fundCurrency: this.editForm.get(['fundCurrency'])!.value,
      isEtf: this.editForm.get(['isEtf'])!.value,
      isEtc: this.editForm.get(['isEtc'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIndex>>): void {
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
}
