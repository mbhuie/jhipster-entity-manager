import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EntityManagerTestModule } from '../../../test.module';
import { IndexETFUpdateComponent } from 'app/entities/index-etf/index-etf-update.component';
import { IndexETFService } from 'app/entities/index-etf/index-etf.service';
import { IndexETF } from 'app/shared/model/index-etf.model';

describe('Component Tests', () => {
  describe('IndexETF Management Update Component', () => {
    let comp: IndexETFUpdateComponent;
    let fixture: ComponentFixture<IndexETFUpdateComponent>;
    let service: IndexETFService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EntityManagerTestModule],
        declarations: [IndexETFUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(IndexETFUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IndexETFUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IndexETFService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IndexETF(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new IndexETF();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
