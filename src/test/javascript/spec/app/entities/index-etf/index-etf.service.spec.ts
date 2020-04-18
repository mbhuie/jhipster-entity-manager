import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { IndexETFService } from 'app/entities/index-etf/index-etf.service';
import { IIndexETF, IndexETF } from 'app/shared/model/index-etf.model';

describe('Service Tests', () => {
  describe('IndexETF Service', () => {
    let injector: TestBed;
    let service: IndexETFService;
    let httpMock: HttpTestingController;
    let elemDefault: IIndexETF;
    let expectedResult: IIndexETF | IIndexETF[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(IndexETFService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new IndexETF(
        0,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            asAtDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a IndexETF', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            asAtDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            asAtDate: currentDate
          },
          returnedFromService
        );

        service.create(new IndexETF()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a IndexETF', () => {
        const returnedFromService = Object.assign(
          {
            basketCurrency: 'BBBBBB',
            asAtDate: currentDate.format(DATE_FORMAT),
            basketInstrument: 'BBBBBB',
            basketInstrumentType: 'BBBBBB',
            basketMinorUnits: 1,
            basketName: 'BBBBBB',
            basketRic: 'BBBBBB',
            basketType: 'BBBBBB',
            family: 'BBBBBB',
            fundCurrency: 'BBBBBB',
            isEtf: true,
            isEtc: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            asAtDate: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of IndexETF', () => {
        const returnedFromService = Object.assign(
          {
            basketCurrency: 'BBBBBB',
            asAtDate: currentDate.format(DATE_FORMAT),
            basketInstrument: 'BBBBBB',
            basketInstrumentType: 'BBBBBB',
            basketMinorUnits: 1,
            basketName: 'BBBBBB',
            basketRic: 'BBBBBB',
            basketType: 'BBBBBB',
            family: 'BBBBBB',
            fundCurrency: 'BBBBBB',
            isEtf: true,
            isEtc: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            asAtDate: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a IndexETF', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
