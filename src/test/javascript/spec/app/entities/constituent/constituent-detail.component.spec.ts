import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EntityManagerTestModule } from '../../../test.module';
import { ConstituentDetailComponent } from 'app/entities/constituent/constituent-detail.component';
import { Constituent } from 'app/shared/model/constituent.model';

describe('Component Tests', () => {
  describe('Constituent Management Detail Component', () => {
    let comp: ConstituentDetailComponent;
    let fixture: ComponentFixture<ConstituentDetailComponent>;
    const route = ({ data: of({ constituent: new Constituent(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EntityManagerTestModule],
        declarations: [ConstituentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ConstituentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConstituentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load constituent on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.constituent).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
