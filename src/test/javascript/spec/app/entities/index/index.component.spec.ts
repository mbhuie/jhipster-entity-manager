import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EntityManagerTestModule } from '../../../test.module';
import { IndexComponent } from 'app/entities/index/index.component';
import { IndexService } from 'app/entities/index/index.service';
import { Index } from 'app/shared/model/index.model';

describe('Component Tests', () => {
  describe('Index Management Component', () => {
    let comp: IndexComponent;
    let fixture: ComponentFixture<IndexComponent>;
    let service: IndexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EntityManagerTestModule],
        declarations: [IndexComponent]
      })
        .overrideTemplate(IndexComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IndexComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IndexService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Index(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.indices && comp.indices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
