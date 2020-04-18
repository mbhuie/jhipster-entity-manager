import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EntityManagerTestModule } from '../../../test.module';
import { IndexETFDetailComponent } from 'app/entities/index-etf/index-etf-detail.component';
import { IndexETF } from 'app/shared/model/index-etf.model';

describe('Component Tests', () => {
  describe('IndexETF Management Detail Component', () => {
    let comp: IndexETFDetailComponent;
    let fixture: ComponentFixture<IndexETFDetailComponent>;
    const route = ({ data: of({ indexETF: new IndexETF(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EntityManagerTestModule],
        declarations: [IndexETFDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(IndexETFDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IndexETFDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load indexETF on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.indexETF).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
