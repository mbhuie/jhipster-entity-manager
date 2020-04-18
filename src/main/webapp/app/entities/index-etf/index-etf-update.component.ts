import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIndexETF, IndexETF } from 'app/shared/model/index-etf.model';
import { IndexETFService } from './index-etf.service';

@Component({
  selector: 'jhi-index-etf-update',
  templateUrl: './index-etf-update.component.html'
})
export class IndexETFUpdateComponent implements OnInit {
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

  constructor(protected indexETFService: IndexETFService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ indexETF }) => {
      this.updateForm(indexETF);
    });
  }

  updateForm(indexETF: IIndexETF): void {
    this.editForm.patchValue({
      id: indexETF.id,
      basketCurrency: indexETF.basketCurrency,
      asAtDate: indexETF.asAtDate,
      basketInstrument: indexETF.basketInstrument,
      basketInstrumentType: indexETF.basketInstrumentType,
      basketMinorUnits: indexETF.basketMinorUnits,
      basketName: indexETF.basketName,
      basketRic: indexETF.basketRic,
      basketType: indexETF.basketType,
      family: indexETF.family,
      fundCurrency: indexETF.fundCurrency,
      isEtf: indexETF.isEtf,
      isEtc: indexETF.isEtc
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const indexETF = this.createFromForm();
    if (indexETF.id !== undefined) {
      this.subscribeToSaveResponse(this.indexETFService.update(indexETF));
    } else {
      this.subscribeToSaveResponse(this.indexETFService.create(indexETF));
    }
  }

  private createFromForm(): IIndexETF {
    return {
      ...new IndexETF(),
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIndexETF>>): void {
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
