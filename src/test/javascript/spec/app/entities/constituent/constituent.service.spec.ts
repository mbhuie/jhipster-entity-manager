import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ConstituentService } from 'app/entities/constituent/constituent.service';
import { IConstituent, Constituent } from 'app/shared/model/constituent.model';

describe('Service Tests', () => {
  describe('Constituent Service', () => {
    let injector: TestBed;
    let service: ConstituentService;
    let httpMock: HttpTestingController;
    let elemDefault: IConstituent;
    let expectedResult: IConstituent | IConstituent[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ConstituentService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Constituent(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Constituent', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Constituent()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Constituent', () => {
        const returnedFromService = Object.assign(
          {
            ric: 'BBBBBB',
            isin: 'BBBBBB',
            sedol: 'BBBBBB',
            currency: 'BBBBBB',
            issueType: 'BBBBBB',
            mic: 'BBBBBB',
            centerCode: 'BBBBBB',
            sharesOutstanding: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Constituent', () => {
        const returnedFromService = Object.assign(
          {
            ric: 'BBBBBB',
            isin: 'BBBBBB',
            sedol: 'BBBBBB',
            currency: 'BBBBBB',
            issueType: 'BBBBBB',
            mic: 'BBBBBB',
            centerCode: 'BBBBBB',
            sharesOutstanding: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Constituent', () => {
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
