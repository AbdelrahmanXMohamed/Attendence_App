export interface vacationRequestResponse{
    type: VacationType

    status:VacationStatus

    startDate: Date

    endDate:Date

    comment:string

    numberOfDay:number
}