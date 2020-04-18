import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EntityManagerTestModule } from '../../../test.module';
import { ConstituentUpdateComponent } from 'app/entities/constituent/constituent-update.component';
import { ConstituentService } from 'app/entities/constituent/constituent.service';
import { Constituent } from 'app/shared/model/constituent.model';

describe('Component Tests', () => {
  describe('Constituent Management Update Component', () => {
    let comp: ConstituentUpdateComponent;
    let fixture: ComponentFixture<ConstituentUpdateComponent>;
    let service: ConstituentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EntityManagerTestModule],
        declarations: [ConstituentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ConstituentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConstituentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConstituentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Constituent(123);
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
        const entity = new Constituent();
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
