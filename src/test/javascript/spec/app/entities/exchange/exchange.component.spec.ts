import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EntityManagerTestModule } from '../../../test.module';
import { ExchangeComponent } from 'app/entities/exchange/exchange.component';
import { ExchangeService } from 'app/entities/exchange/exchange.service';
import { Exchange } from 'app/shared/model/exchange.model';

describe('Component Tests', () => {
  describe('Exchange Management Component', () => {
    let comp: ExchangeComponent;
    let fixture: ComponentFixture<ExchangeComponent>;
    let service: ExchangeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EntityManagerTestModule],
        declarations: [ExchangeComponent]
      })
        .overrideTemplate(ExchangeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExchangeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExchangeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Exchange(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.exchanges && comp.exchanges[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
