import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EntityManagerTestModule } from '../../../test.module';
import { ConstituentComponent } from 'app/entities/constituent/constituent.component';
import { ConstituentService } from 'app/entities/constituent/constituent.service';
import { Constituent } from 'app/shared/model/constituent.model';

describe('Component Tests', () => {
  describe('Constituent Management Component', () => {
    let comp: ConstituentComponent;
    let fixture: ComponentFixture<ConstituentComponent>;
    let service: ConstituentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EntityManagerTestModule],
        declarations: [ConstituentComponent]
      })
        .overrideTemplate(ConstituentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConstituentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConstituentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Constituent(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.constituents && comp.constituents[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
