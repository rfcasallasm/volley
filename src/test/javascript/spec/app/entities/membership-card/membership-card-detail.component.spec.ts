/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VolleyTestModule } from '../../../test.module';
import { MembershipCardDetailComponent } from 'app/entities/membership-card/membership-card-detail.component';
import { MembershipCard } from 'app/shared/model/membership-card.model';

describe('Component Tests', () => {
    describe('MembershipCard Management Detail Component', () => {
        let comp: MembershipCardDetailComponent;
        let fixture: ComponentFixture<MembershipCardDetailComponent>;
        const route = ({ data: of({ membershipCard: new MembershipCard(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VolleyTestModule],
                declarations: [MembershipCardDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MembershipCardDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MembershipCardDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.membershipCard).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
