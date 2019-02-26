/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { MembershipCardService } from 'app/entities/membership-card/membership-card.service';
import { IMembershipCard, MembershipCard } from 'app/shared/model/membership-card.model';

describe('Service Tests', () => {
    describe('MembershipCard Service', () => {
        let injector: TestBed;
        let service: MembershipCardService;
        let httpMock: HttpTestingController;
        let elemDefault: IMembershipCard;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(MembershipCardService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new MembershipCard(0, 0, currentDate, 'AAAAAAA', currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        commencementDate: currentDate.format(DATE_FORMAT),
                        birthDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a MembershipCard', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        commencementDate: currentDate.format(DATE_FORMAT),
                        birthDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        commencementDate: currentDate,
                        birthDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new MembershipCard(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a MembershipCard', async () => {
                const returnedFromService = Object.assign(
                    {
                        membershipNumber: 1,
                        commencementDate: currentDate.format(DATE_FORMAT),
                        name: 'BBBBBB',
                        birthDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        commencementDate: currentDate,
                        birthDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of MembershipCard', async () => {
                const returnedFromService = Object.assign(
                    {
                        membershipNumber: 1,
                        commencementDate: currentDate.format(DATE_FORMAT),
                        name: 'BBBBBB',
                        birthDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        commencementDate: currentDate,
                        birthDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a MembershipCard', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
