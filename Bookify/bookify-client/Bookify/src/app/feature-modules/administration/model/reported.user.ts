export interface ReportedUser {
    id?:number,
    reason?:string,
    created?:number,
    reportedUser:{
        id?:string,
        firstName?:string,
        lastName?:string,
        email?:string,
        blocked: boolean
    },
    createdBy:{
        id?:string,
        firstName?:string,
        lastName?:string,
        email?:string
    }

}
