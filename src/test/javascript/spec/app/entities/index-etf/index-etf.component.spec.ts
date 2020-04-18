import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EntityManagerTestModule } from '../../../test.module';
import { IndexETFComponent } from 'app/entities/index-etf/index-etf.component';
import { IndexETFService } from 'app/entities/index-etf/index-etf.service';
import { IndexETF } from 'app/shared/model/index-etf.model';

describe('Component Tests', () => {
  describe('IndexETF Management Component', () => {
    let comp: IndexETFComponent;
    let fixture: ComponentFixture<IndexETFComponent>;
    let service: IndexETFService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EntityManagerTestModule],
        declarations: [IndexETFComponent]
      })
        .overrideTemplate(IndexETFComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IndexETFComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IndexETFService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new IndexETF(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.indexETFS && comp.indexETFS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
